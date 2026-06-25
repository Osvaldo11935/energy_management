package telas;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.*;

import modeloFiles.SubestacaoFile;
import models.SubestacaoModelo;

public class TabelaSubestacao extends JFrame {

    private final SubestacaoFile subestacaoFile = new SubestacaoFile(new SubestacaoModelo());

    private List<SubestacaoModelo> subestacao;

    private JPanel painelTabela;

    public TabelaSubestacao() {

        setTitle("Subestações");
        setSize(700, 400);
        setLocationRelativeTo(null);

        carregarTabela();
    }

    private void carregarTabela() {

        subestacao = subestacaoFile.listar();

        String[] colunas = {
                "ID",
                "Codigo",
                "Nome",
                "Capacidade",
                "Tensão Nominal",
                "Responsavel"
        };

        List<Object[]> dados = subestacao.stream()
                .map(u -> new Object[]{
                        u.getId(),
                        u.getCodigo(),
                        u.getNome(),
                        u.getCapacidade(),
                        u.getTensaoNominal(),
                        u.getUsuarioId()
                })
                .toList();

        JPanel novaTabela =
                TabelaGerador.criarTabelaComAcoes(
                        colunas,
                        dados,
                        criarAcoes(),
                        "Novo",
                        this::abrirNovoSubestacao
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

                        SubestacaoModelo subestacao =
                                buscarSubestacao(dadosLinha);

                        FormWindow tela =
                                new FormWindow(
                                        "Atualizar Subestação",
                                        456,
                                        628,
                                        new FormSubestacao(subestacao)
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
                                        TabelaSubestacao.this,
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

                            subestacaoFile.remover(id);

                            carregarTabela();

                            JOptionPane.showMessageDialog(
                                    TabelaSubestacao.this,
                                    "Removido com sucesso!"
                            );
                        }
                    }
                }
        );
    }

    private SubestacaoModelo buscarSubestacao(
            Object[] dadosLinha
    ) {

        int id = getId(dadosLinha);

        return subestacao.stream()
                .filter(
                        u -> u.getId() == id
                )
                .findFirst()
                .orElse(null);
    }

    private int getId(
            Object[] dadosLinha
    ) {

        return Integer.parseInt(
                dadosLinha[0].toString()
        );
    }

    private void abrirNovoSubestacao() {

        FormWindow tela =
                new FormWindow(
                        "Nova Subestação",
                        456,
                        628,
                        new FormSubestacao()
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

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () ->
                        new TabelaSubestacao()
                                .setVisible(true)
        );
    }
}