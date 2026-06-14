package telas;

import models.MenuModelo;
import modeloFiles.MenuFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class TabelaMenu extends JFrame {

    private final MenuFile menuFile =
            new MenuFile(new MenuModelo());

    private List<MenuModelo> menu;

    private JPanel painelTabela;

    public TabelaMenu() {

        setTitle("Menus");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        carregarTabela();
    }

    private void carregarTabela() {

        menu = menuFile.listar();

        String[] colunas = {
                "ID",
                "Codigo",
                "Nome",
                "Descrição",
                "Icone",
                "Caminho Classe",
                "Ordem",
                "Nivel Acesso"
        };

        List<Object[]> dados =
                menu.stream()
                        .map(u -> new Object[]{
                                u.getId(),
                                u.getCodigo(),
                                u.getNome(),
                                u.getDescricao(),
                                u.getIcone(),
                                u.getCaminhoClasse(),
                                u.getOrdem(),
                                u.getNivelMinimoAcesso()
                        })
                        .toList();

        JPanel novaTabela =
                TabelaGerador.criarTabelaComAcoes(
                        colunas,
                        dados,
                        criarAcoes(),
                        "Novo",
                        this::abrirNovoMenu
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

                        MenuModelo menuItem =
                                buscarMenu(dadosLinha);

                        FormWindow tela =
                                new FormWindow(
                                        "Atualizar Menu",
                                        356,
                                        554,
                                        new FormMenu(menuItem)
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
                                        TabelaMenu.this,
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

                            menuFile.remover(id);

                            carregarTabela();

                            JOptionPane.showMessageDialog(
                                    TabelaMenu.this,
                                    "Removido!"
                            );
                        }
                    }
                }
        );
    }

    private MenuModelo buscarMenu(
            Object[] dadosLinha
    ) {

        int id =
                getId(dadosLinha);

        return menu.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private int getId(Object[] dadosLinha) {

        return Integer.parseInt(
                dadosLinha[0].toString()
        );
    }

    private void abrirNovoMenu() {

        FormWindow tela =
                new FormWindow(
                        "Novo Menu",
                        356,
                        554,
                        new FormMenu()
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
                        new TabelaMenu()
                                .setVisible(true)
        );
    }
}