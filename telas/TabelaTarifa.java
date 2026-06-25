package telas;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import modeloFiles.TarifaFile;
import models.TarifaModelo;

public class TabelaTarifa extends JFrame {

    private final TarifaFile arquivo =
            new TarifaFile(new TarifaModelo());

    private List<TarifaModelo> tarifas;

    private JPanel painelTabela;

    public TabelaTarifa() {

        setTitle("Tarifas");
        setSize(700, 400);
        setLocationRelativeTo(null);
        
        carregarTabela();
    }

    private void carregarTabela() {

        tarifas = arquivo.listar();

        String[] colunas = {
                "ID",
                "Nome da Tarifa",
                "Preço em (Kwh)",
                "Taxa Fixa(%)",
                "Multa por Atraso",
                "Data Vigor"
        };

        List<Object[]> dados =
                new ArrayList<>();

        for (TarifaModelo p : tarifas) {

            dados.add(
                    new Object[]{
                            p.getId(),
                            p.getNomeTarifa(),
                            p.getPrecoKwh(),
                            p.getTaxaFixa(),
                            p.getMultaAtraso(),
                            p.getDataVigor()
                    }
            );
        }

        JPanel novaTabela =
                TabelaGerador.criarTabelaComAcoes(
                        colunas,
                        dados,
                        criarAcoes(),
                        "Novo",
                        this::abrirNovoTarifa
                );

        if (painelTabela != null) {
            remove(painelTabela);
        }

        painelTabela = novaTabela;

        add(painelTabela, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private List<TabelaGerador.AcaoTabela> criarAcoes() {

        List<TabelaGerador.AcaoTabela> acoes =
                new ArrayList<>();

        acoes.add(new TabelaGerador.AcaoTabela() {
                    @Override
                    public void executar(int linha, Object[] dadosLinha) {

                        TarifaModelo Tarifa = buscarTarifa(dadosLinha);
                        FormWindow tela =
                            new FormWindow(
                                    "Atualizar Tarifa",
                                    456,
                                    348,
                                    new FormTarifa(Tarifa)
                            );

                        tela.addWindowListener(new WindowAdapter() {
                                    @Override
                                    public void windowClosed(WindowEvent e) {
                                        carregarTabela();
                                    }
                                }
                        );

                        tela.setVisible(true);
                    }

                    @Override
                    public String getNome() {
                        return "Editar";
                    }
                }
        );

        acoes.add(new TabelaGerador.AcaoTabela() {
                    @Override
                    public void executar(int linha, Object[] dadosLinha) {

                        int confirm =
                                JOptionPane.showConfirmDialog(
                                        TabelaTarifa.this,
                                        "Remover "
                                                + dadosLinha[1]
                                                + "?",
                                        "Confirmação",
                                        JOptionPane.YES_NO_OPTION
                                );

                        if (confirm == JOptionPane.YES_OPTION) {

                            int id = getId(dadosLinha);

                            arquivo.remover(id);

                            carregarTabela();

                            JOptionPane.showMessageDialog(TabelaTarifa.this, "Removido!");
                        }
                    }

                    @Override
                    public String getNome() {
                        return "Remover";
                    }
                }
        );

        acoes.add(new TabelaGerador.AcaoTabela() {
                    @Override
                    public void executar(int linha, Object[] dadosLinha) {
                        SwingUtilities.invokeLater(() -> new TabelaFaixaConsumo(Integer.parseInt(dadosLinha[0].toString())).setVisible(true));
                    }

                    @Override
                    public String getNome() {
                        return "Faixa de Consumo";
                    }
                }
        );
        return acoes;
    }

    private TarifaModelo buscarTarifa(
            Object[] dadosLinha
    ) {

        int id = getId(dadosLinha);
        return tarifas
                .stream()
                .filter(p -> p.getId()  == id)
                .findFirst()
                .orElse(null);
    }

    private int getId(Object[] dadosLinha) {
        return Integer.parseInt(
                dadosLinha[0]
                        .toString()
        );
    }

    private void abrirNovoTarifa() {
       
        FormWindow tela =new FormWindow(
            "Nova Tarifa",
            456,
            348,
            new FormTarifa()
        );


        tela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                    carregarTabela();
                }
            }
        );

        tela.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->new TabelaTarifa().setVisible(true));
    }
}