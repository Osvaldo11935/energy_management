package telas;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.*;

import modeloFiles.AreaDistribuicaoFile;
import models.AreaDistribuicaoModelo;

public class TabelaAreaDistribuicao extends JFrame {

    private final AreaDistribuicaoFile areaDistribuicaoFile =
            new AreaDistribuicaoFile(new AreaDistribuicaoModelo());

    private List<AreaDistribuicaoModelo> areaDistribuicao;

    private JPanel painelTabela;

    public TabelaAreaDistribuicao() {

        setTitle("Área de Distribuições");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        carregarTabela();
    }

    private void carregarTabela() {

        areaDistribuicao =
                areaDistribuicaoFile.listar();

        String[] colunas = {
                "ID",
                "Provincia",
                "Municipio",
                "Comuna",
                "Bairro",
                "Subestação"
        };

        List<Object[]> dados =
                areaDistribuicao.stream()
                        .map(u -> new Object[]{
                                u.getId(),
                                u.getProvincia(),
                                u.getMunicipio(),
                                u.getComuna(),
                                u.getBairro(),
                                u.getSubestacaoId()
                        })
                        .toList();

        JPanel novaTabela =
                TabelaGerador.criarTabelaComAcoes(
                        colunas,
                        dados,
                        criarAcoes(),
                        "Novo",
                        this::abrirNovaAreaDistribuicao
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

                        AreaDistribuicaoModelo area =
                                buscarAreaDistribuicao(dadosLinha);

                        FormWindow tela =
                                new FormWindow(
                                        "Atualizar Área de Distribuição",
                                        456,
                                        387,
                                        new FormAreaDistribuicao(area)
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
                                        TabelaAreaDistribuicao.this,
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

                            areaDistribuicaoFile.remover(id);

                            carregarTabela();

                            JOptionPane.showMessageDialog(
                                    TabelaAreaDistribuicao.this,
                                    "Removido!"
                            );
                        }
                    }
                }
        );
    }

    private AreaDistribuicaoModelo buscarAreaDistribuicao(
            Object[] dadosLinha
    ) {

        int id =
                getId(dadosLinha);

        return areaDistribuicao.stream()
                .filter(
                        u -> u.getId() == id
                )
                .findFirst()
                .orElse(null);
    }

    private int getId(Object[] dadosLinha) {

        return Integer.parseInt(
                dadosLinha[0].toString()
        );
    }

    private void abrirNovaAreaDistribuicao() {

        FormWindow tela =
                new FormWindow(
                        "Nova Área de Distribuição",
                        456,
                        387,
                        new FormAreaDistribuicao()
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
        SwingUtilities.invokeLater(() ->new TabelaAreaDistribuicao().setVisible(true));
    }
}