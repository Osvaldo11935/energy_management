package telas;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import modeloFiles.AtalhoFile;
import models.AtalhoModelo;
import utils.Session;

public class TabelaAtalho extends JPanel {

    private final AtalhoFile arquivo = AtalhoFile.instaciar();

    private List<AtalhoModelo> atalhos;

    private JPanel painelTabela;

    public TabelaAtalho() {
        carregarTabela();
    }

    private void carregarTabela() {

        atalhos = arquivo.buscarAtalhoPorUsuarioId(Session.getUsuario().getId());

        String[] colunas = {
                "ID",
                "Nome",
                "Descrição",
                "Menu",
                "Teclado"
        };

        List<Object[]> dados =
                new ArrayList<>();

        for (AtalhoModelo p : atalhos) {

            dados.add(
                    new Object[]{
                            p.getId(),
                            p.getNome(),
                            p.getDescricao(),
                            p.getMenu().getNome(),
                            p.getTeclado()
                    }
            );
        }

        JPanel novaTabela =
                TabelaGerador.criarTabelaComAcoes(
                        colunas,
                        dados,
                        criarAcoes(),
                        "Novo",
                        this::abrirNovoAtalho
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

                        AtalhoModelo Atalho = buscarAtalho(dadosLinha);
                        FormWindow tela =
                            new FormWindow(
                                    "Atualizar Atalho",
                                    300,
                                    300,
                                    new FormAtalho(Atalho)
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
                                        TabelaAtalho.this,
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

                            JOptionPane.showMessageDialog(TabelaAtalho.this, "Removido!");
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

    private AtalhoModelo buscarAtalho(
            Object[] dadosLinha
    ) {

        int id = getId(dadosLinha);
        return atalhos
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

    private void abrirNovoAtalho() {
       
        FormWindow tela = new FormWindow(
            "Atualizar Atalho",
            360,
            400,
            new FormAtalho()
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
        SwingUtilities.invokeLater(() ->new TabelaAtalho().setVisible(true));
    }
}