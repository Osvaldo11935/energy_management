package telas;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import modeloFiles.PerfilFile;
import models.PerfilModelo;

public class TabelaPerfil extends JFrame {

    private final PerfilFile arquivo =
            new PerfilFile(new PerfilModelo());

    private List<PerfilModelo> perfis;

    private JPanel painelTabela;

    public TabelaPerfil() {

        setTitle("Perfis");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        carregarTabela();
    }

    private void carregarTabela() {

        perfis = arquivo.listar();

        String[] colunas = {
                "ID",
                "Nome",
                "Descrição"
        };

        List<Object[]> dados =
                new ArrayList<>();

        for (PerfilModelo p : perfis) {

            dados.add(
                    new Object[]{
                            p.getId(),
                            p.getNome(),
                            p.getDescricao()
                    }
            );
        }

        JPanel novaTabela =
                TabelaGerador.criarTabelaComAcoes(
                        colunas,
                        dados,
                        criarAcoes(),
                        "Novo",
                        this::abrirNovoPerfil
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

                        PerfilModelo perfil = buscarPerfil(dadosLinha);
                        FormWindow tela =
                            new FormWindow(
                                    "Atualizar Perfil",
                                    300,
                                    300,
                                    new FormPerfil(perfil)
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
                                        TabelaPerfil.this,
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

                            JOptionPane.showMessageDialog(TabelaPerfil.this, "Removido!");
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

    private PerfilModelo buscarPerfil(
            Object[] dadosLinha
    ) {

        int id = getId(dadosLinha);
        return perfis
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

    private void abrirNovoPerfil() {
       
        FormWindow tela =new FormWindow(
            "Atualizar Perfil",
            300,
            300,
            new FormPerfil()
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
        SwingUtilities.invokeLater(() ->new TabelaPerfil().setVisible(true));
    }
}