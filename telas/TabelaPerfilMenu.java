package telas;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.*;

import modeloFiles.PerfilMenuFile;
import models.PerfilMenuModelo;

public class TabelaPerfilMenu extends JFrame {

    private final PerfilMenuFile perfilMenuFile =
            new PerfilMenuFile(
                    new PerfilMenuModelo()
            );

    private List<PerfilMenuModelo> perfilMenu;

    private JPanel painelTabela;

    public TabelaPerfilMenu() {

        setTitle("Perfil Menus");

        setSize(700, 400);

        setLocationRelativeTo(null);

        carregarTabela();
    }

    private void carregarTabela() {

        perfilMenu =
                perfilMenuFile.listar();

        String[] colunas = {
                "ID",
                "Perfil",
                "Menu",
                "Pode Visualizar",
                "Pode Criar",
                "Pode Editar",
                "Pode Eliminar"
        };

        List<Object[]> dados =
                perfilMenu
                        .stream()
                        .map(
                                u ->
                                        new Object[]{
                                                u.getId(),
                                                u.getPerfil()
                                                        .getNome(),
                                                u.getMenu()
                                                        .getNome(),
                                                u.isPodeVisualizar(),
                                                u.isPodeCriar(),
                                                u.isPodeEditar(),
                                                u.isPodeEliminar()
                                        }
                        )
                        .toList();

        JPanel novaTabela =
                TabelaGerador
                        .criarTabelaComAcoes(
                                colunas,
                                dados,
                                criarAcoes(),
                                "Novo",
                                this::abrirNovoPerfilMenu
                        );

        if (painelTabela != null) {
            remove(
                    painelTabela
            );
        }

        painelTabela =
                novaTabela;

        add(
                painelTabela,
                BorderLayout.CENTER
        );

        revalidate();

        repaint();
    }

    private List<TabelaGerador.AcaoTabela>
    criarAcoes() {

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

                        PerfilMenuModelo item =
                                buscarPerfilMenu(
                                        dadosLinha
                                );

                        FormWindow tela =
                                new FormWindow(
                                        "Atualizar Perfil Menu",
                                        466,
                                        270,
                                        new FormPerfilMenu(
                                                item
                                        )
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

                        tela.setVisible(
                                true
                        );
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
                                        TabelaPerfilMenu.this,
                                        "Remover "
                                                + dadosLinha[1]
                                                + "?",
                                        "Confirmação",
                                        JOptionPane.YES_NO_OPTION
                                );

                        if (confirm
                                == JOptionPane.YES_OPTION) {

                            int id =
                                    getId(
                                            dadosLinha
                                    );

                            perfilMenuFile
                                    .remover(
                                            id
                                    );

                            carregarTabela();

                            JOptionPane
                                    .showMessageDialog(
                                            TabelaPerfilMenu.this,
                                            "Removido!"
                                    );
                        }
                    }
                }
        );
    }

    private PerfilMenuModelo buscarPerfilMenu(
            Object[] dadosLinha
    ) {

        int id =
                getId(
                        dadosLinha
                );

        return perfilMenu
                .stream()
                .filter(
                        u ->
                                u.getId()
                                        == id
                )
                .findFirst()
                .orElse(
                        null
                );
    }

    private int getId(
            Object[] dadosLinha
    ) {

        return Integer.parseInt(
                dadosLinha[0]
                        .toString()
        );
    }

    private void abrirNovoPerfilMenu() {

        FormWindow tela =
                new FormWindow(
                        "Novo Perfil Menu",
                        466,
                        270,
                        new FormPerfilMenu()
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

        tela.setVisible(
                true
        );
    }

    public static void main(
            String[] args
    ) {

        SwingUtilities
                .invokeLater(
                        () ->
                                new TabelaPerfilMenu()
                                        .setVisible(
                                                true
                                        )
                );
    }
}