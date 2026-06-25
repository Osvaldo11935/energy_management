package servicos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javax.swing.*;
import enums.*;
import modeloFiles.*;
import models.*;
import utils.Session;

public class FaturacaoBackgroundServico extends SwingWorker<Integer, String> {

    //private static final DateTimeFormatter DATA = DateTimeFormatter.ISO_LOCAL_DATE;
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/M/d");
    private static FaturacaoBackgroundServico instance;

    private final FaturaFile faturaFile = FaturaFile.instaciar();
    private final LeituraConsumoFile leituraFile = LeituraConsumoFile.instaciar();
    private final TarifaFile tarifaFile = TarifaFile.instaciar();
    private final ClienteFile clienteFile = ClienteFile.instaciar();
    private final ContadorFile contadorFile = ContadorFile.instaciar();

    private final List<String> logs = new ArrayList<>();

    private boolean executando;
    private int geradas;
    private int erros;
    private int processados;

    private ProgressMonitor monitor;

    public static synchronized FaturacaoBackgroundServico instancia() {
        if (instance == null) instance = new FaturacaoBackgroundServico();
        return instance;
    }

    @Override
    protected Integer doInBackground() {

        reset();

        try {
            TarifaModelo tarifa = obterTarifa();
            List<ClienteModelo> clientes =
                    clienteFile.buscarClientePorTipoContrato(TipoContrato.POS_PAGO);

            int total = clientes.size();

            monitor = new ProgressMonitor(
                    null,
                    "Processando Facturação...",
                    "Iniciando...",
                    0,
                    total
            );

            monitor.setMillisToPopup(0);
            monitor.setMillisToDecideToPopup(0);

            int index = 0;

            for (ClienteModelo cliente : clientes) {

                if (monitor.isCanceled()) {
                    publish("Processo cancelado pelo utilizador");
                    break;
                }

                processar(cliente, tarifa);

                index++;

                int progress = (int) (((double) index / total) * 100);

                setProgress(progress);

                monitor.setProgress(index);
                monitor.setNote("Cliente " + index + "/" + total);

                publish("Cliente processado: " + cliente.getId());

                Thread.sleep(20);
            }

            atualizarMultas();
            verificarCortes();

            return geradas;

        } catch (Exception e) {
            publish("ERRO GRAVE: " + e.getMessage());
            return geradas;

        } finally {
            executando = false;
        }
    }

    private void processar(ClienteModelo cliente, TarifaModelo tarifa) {

        try {

            ContadorModelo contador =
                    contadorFile.buscarPorClienteId(cliente.getId());

            validar(contador);

            LeituraConsumoModelo leitura =
                    leituraFile.buscarUltimaLeitura(contador.getId());

            validar(leitura);

            if (faturaFile.existePorLeituraId(leitura.getId())) {
                publish("Já faturado: cliente " + cliente.getId());
                return;
            }

            double consumo = Math.abs(leitura.getLeituraActual() - leitura.getLeituraAnterior());

            if (consumo <= 0) {
                publish("Consumo zero: cliente " + cliente.getId());
                return;
            }

            Calculo calc = calcular(consumo, tarifa);

            FaturaModelo factura = criarFactura(cliente, contador, leitura, tarifa, consumo, calc.total);

            factura.salvarDados();

            geradas++;
            processados++;
             
            publish("Factura gerada: Quantidade processadas " + processados + " valor: " + calc.total);
            publish("Factura gerada: cliente " + cliente.getId() + " valor: " + calc.total);

        } catch (Exception ex) {
            erros++;
            publish("Erro cliente " + erros + ": " + ex.getMessage());
            publish("Erro cliente " + cliente.getId() + ": " + ex.getMessage());
        }
    }

    private void atualizarMultas() {

        List<FaturaModelo> vencidas = faturaFile.buscarVencidasNaoPagas();

        publish("Atualizando multas: " + vencidas.size());

        for (FaturaModelo f : vencidas) {

            int atraso = (int) Math.max(0,
                    ChronoUnit.DAYS.between(
                            LocalDate.parse(f.getDataVencimento(), formato),
                            LocalDate.now()
                    )
            );

            if (atraso == 0) continue;

            double multa = f.getValorTotal() * (0.02 + (0.00033 * atraso));
            f.setStatus(EstadoFatura.VENCIDO.toString());
            f.setValorMulta(multa);
            f.setValorActualizado(f.getValorTotal() + multa);

            f.atualizarDados();

            publish("Multa aplicada fatura " + f.getId() + " atraso: " + atraso);
        }
    }

    private void verificarCortes() {

        List<FaturaModelo> lista = faturaFile.buscarFaturasComAtrasoSuperior(Session.getDiaAtraso());

        publish("Cortes: " + lista.size());

        lista.stream()
                .map(FaturaModelo::getClienteId)
                .distinct()
                .forEach(id -> {
                    clienteFile.marcarParaCorte(id);
                    publish("Cliente marcado para corte: " + id);
                });
    }

    private TarifaModelo obterTarifa() {
        return tarifaFile.listar().getFirst();
    }

    private Calculo calcular(double consumo, TarifaModelo tarifa) {

        double subtotal = tarifa.getTaxaFixa();

        for (FaixaConsumoModelo f : FaixaConsumoFile.instaciar().buscarPorTarifaId(tarifa.getId())) {

            if (consumo <= 0) break;

            double usado = Math.min(consumo, f.getLimiteMaximo());

            subtotal += usado * f.getPreco();

            consumo -= usado;
        }

        double iva = subtotal * 0.14;

        return new Calculo(subtotal, iva, subtotal + iva);
    }

    private FaturaModelo criarFactura(
            ClienteModelo cliente,
            ContadorModelo contador,
            LeituraConsumoModelo leitura,
            TarifaModelo tarifa,
            double consumo,
            double total
    ) {

        int id = Optional.ofNullable(faturaFile.listar())
                .filter(v -> !v.isEmpty())
                .map(v -> v.getLast().getId() + 1)
                .orElse(1);

        LocalDate hoje = LocalDate.now();

        return new FaturaModelo(
                id,
                cliente.getId(),
                contador.getId(),
                leitura.getId(),
                tarifa.getId(),
                consumo,
                total,
                0.0,
                total,
                hoje.toString(),
                hoje.plusDays(Session.getDiaToleranca()).toString(),
                hoje.toString(),
                EstadoFatura.PENDENTE.toString(),
                0,
                gerarNumero(id)
        );
    }

    private String gerarNumero(int id) {
        LocalDate d = LocalDate.now();
        return "F-%d-%02d-%05d"
                .formatted(d.getYear(), d.getMonthValue(), id);
    }

    private void validar(Object o) throws Exception {
        if (o == null)
            throw new Exception("Dados obrigatórios não encontrados");
    }

    private void reset() {
        executando = true;
        geradas = 0;
        erros = 0;
        processados = 0;
        logs.clear();

        setProgress(0);
    }

    @Override
    protected void process(List<String> chunks) {
        logs.addAll(chunks);
        for (String msg : chunks) {
            System.out.println(msg);
        }
    }

    @Override
    protected void done() {
        executando = false;

        try {
            int total = get();
            publish("Finalizado: " + total + " facturas");

        } catch (Exception e) {
            publish("Erro final: " + e.getMessage());
        }
    }

    public void executarFacturacao() {
        if (!executando) execute();
    }

    public boolean isExecutando() {
        return executando;
    }

    public List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    public void limparLogs() {
        logs.clear();
    }

    record Calculo(double subtotal, double iva, double total) {}
}