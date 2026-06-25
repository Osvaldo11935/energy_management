package telas;

import models.FaixaConsumoModelo;
import modeloFiles.FaixaConsumoFile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class TabelaFaixaConsumo extends JFrame {

    private final FaixaConsumoFile faixaConsumoFile = FaixaConsumoFile.instaciar();

    private List<FaixaConsumoModelo> faixaConsumos;

    private JPanel painelTabela;
    private int tarifaId;
    public TabelaFaixaConsumo(int tarifaId) {
        this.tarifaId = tarifaId;
        setTitle("FaixaConsumos");
        setSize(700, 400);
        setLocationRelativeTo(null);
        
        carregarTabela();
    }

    private void carregarTabela() {

        faixaConsumos = faixaConsumoFile.buscarPorTarifaId(tarifaId);

        String[] colunas = {
                "ID",
                "Nome",
                "Limite Minimo",
                "Limite Maximo",
                "Preço",
                "Desconto",
                "Descrição"
        };
        List<Object[]> dados =
                faixaConsumos.stream()
                        .map(u -> new Object[]{
                                u.getId(),
                                u.getNome(),
                                u.getLimiteMinimo(),
                                u.getLimiteMaximo(),
                                u.getPreco(),
                                u.getDesconto(),
                                u.getDescricao()
                        })
                        .toList();

        JPanel novaTabela =
                TabelaGerador.criarTabelaComAcoes(
                        colunas,
                        dados,
                        criarAcoes(),
                        "Novo",
                        this::abrirNovoFaixaConsumo
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

        return List.of(

                new TabelaGerador.AcaoTabela() {

                    @Override
                    public String getNome() {
                        return "Editar";
                    }

                    @Override
                    public void executar(
                            int linha,
                            Object[] dadosLinha
                    ) {

                        FaixaConsumoModelo faixaConsumoItem =
                                buscarFaixaConsumo(dadosLinha);

                        FormWindow tela =
                                new FormWindow(
                                        "Atualizar FaixaConsumo",
                                        456,
                                        596,
                                        new FormFaixaConsumo(faixaConsumoItem)
                                );

                        tela.addWindowListener(
                                new WindowAdapter() {

                                    @Override
                                    public void windowClosed(
                                            WindowEvent e
                                    ) {
                                        carregarTabela();
                                    }
                                }
                        );

                        tela.setVisible(true);
                    }
                },

                new TabelaGerador.AcaoTabela() {

                    @Override
                    public String getNome() {
                        return "Remover";
                    }

                    @Override
                    public void executar(
                            int linha,
                            Object[] dadosLinha
                    ) {

                        int confirm =
                                JOptionPane.showConfirmDialog(
                                        TabelaFaixaConsumo.this,
                                        "Remover "
                                                + dadosLinha[1]
                                                + "?",
                                        "Confirmação",
                                        JOptionPane.YES_NO_OPTION
                                );

                        if (confirm
                                == JOptionPane.YES_OPTION) {

                            int id =
                                    getId(dadosLinha);

                            faixaConsumoFile.remover(id);

                            carregarTabela();

                            JOptionPane.showMessageDialog(
                                    TabelaFaixaConsumo.this,
                                    "Removido!"
                            );
                        }
                    }
                }
        );
    }

    private FaixaConsumoModelo buscarFaixaConsumo(
            Object[] dadosLinha
    ) {

        int id =
                getId(dadosLinha);

        return faixaConsumos.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private int getId(Object[] dadosLinha) {

        return Integer.parseInt(
                dadosLinha[0].toString()
        );
    }

    private void abrirNovoFaixaConsumo() {

        FormWindow tela =
                new FormWindow(
                        "Novo FaixaConsumo",
                        456,
                        596,
                        new FormFaixaConsumo(tarifaId)
                );

        tela.addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosed(
                            WindowEvent e
                    ) {
                        carregarTabela();
                    }
                }
        );

        tela.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () ->
                        new TabelaFaixaConsumo(0)
                                .setVisible(true)
        );
    }
}