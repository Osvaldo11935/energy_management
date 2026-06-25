package telas;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import modeloFiles.FaturaFile;
import models.FaturaModelo;

public class TabelaFatura extends JFrame {
    private int clienteId;
    private final FaturaFile arquivo = FaturaFile.instaciar();

    private List<FaturaModelo> faturas;

    private JPanel painelTabela;

    public TabelaFatura(int clienteId) {
        this.clienteId = clienteId;
        setTitle("Faturas");
        setSize(700, 400);
        setLocationRelativeTo(null);
        
        carregarTabela();
    }

    private void carregarTabela() {
         
        faturas = arquivo.buscarPorClienteId(clienteId);

        String[] colunas = {
                "ID",
                "Numero do Contador",
                "Consumo (Kwh)",
                "Data de Emissão",
                "Data de Vencimento",
                "Estado"
        };

        List<Object[]> dados =
                new ArrayList<>();

        for (FaturaModelo p : faturas) {

            dados.add(
                    new Object[]{
                            p.getId(),
                            p.getContador().getNumeroSerie(),
                            p.getConsumoKwh(),
                            p.getDataEmissao(),
                            p.getDataVencimento(),
                            p.getStatus()
                    }
            );
        }

        JPanel novaTabela =
                TabelaGerador.criarTabelaComAcoes(
                        colunas,
                        dados,
                        criarAcoes(),
                        null,
                        null
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

                        int confirm =
                                JOptionPane.showConfirmDialog(
                                        TabelaFatura.this,
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

                            JOptionPane.showMessageDialog(TabelaFatura.this, "Removido!");
                        }
                    }

                    @Override
                    public String getNome() {
                        return "Remover";
                    }
                }
        );
        return acoes;
    }
/*
    private FaturaModelo buscarFatura(Object[] dadosLinha) {

        int id = getId(dadosLinha);
        return faturas
                .stream()
                .filter(p -> p.getId()  == id)
                .findFirst()
                .orElse(null);
    }
 */
    private int getId(Object[] dadosLinha) {
        return Integer.parseInt(
                dadosLinha[0]
                        .toString()
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->new TabelaFatura(0).setVisible(true));
    }
}