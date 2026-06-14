package telas;

import models.MenuModelo;
import modeloFiles.MenuFile;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TabelaMenu extends JFrame {

    private final MenuFile menuFile = new MenuFile(new MenuModelo());

    private final List<MenuModelo> menu;

    public TabelaMenu() {

        setTitle("Menus");
        setSize(700, 400);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menu = menuFile.listar(); 

        String[] colunas = {"ID", "Codigo", "Nome", "Descrição", "Icone", "Caminho Classe", "Ordem", "Nivel Acesso"};

        List<Object[]> dados = menu.stream()
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

        List<TabelaGerador.AcaoTabela> acoes = criarAcoes();

        JPanel tabela = TabelaGerador.criarTabelaComAcoes(
                colunas,
                dados,
                acoes,
                "Novo",
                this::abrirNovoMenu
        );

        add(tabela, BorderLayout.CENTER);
    }

    private List<TabelaGerador.AcaoTabela> criarAcoes() {

        return List.of(

                new TabelaGerador.AcaoTabela() {
                    public String getNome() {
                        return "Editar";
                    }

                    public void executar(int linha, Object[] dadosLinha) {
                        MenuModelo Menu = buscarMenu(dadosLinha);
                        SwingUtilities.invokeLater(() ->
                                 new FormWindow("Atualizar Menu", 356, 554, new FormCadastroMenu(Menu)).setVisible(true)
                        );
                    }
                },

                new TabelaGerador.AcaoTabela() {
                    public String getNome() {
                        return "Remover";
                    }

                    public void executar(int linha, Object[] dadosLinha) {

                        int confirm = JOptionPane.showConfirmDialog(
                                null,
                                "Remover " + dadosLinha[1] + "?"
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(null, "Removido!");
                        }
                    }
                }
            );
    }

    private MenuModelo buscarMenu(Object[] dadosLinha) {
        int id = getId(dadosLinha);
        return menu.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private int getId(Object[] dadosLinha) {
        return Integer.parseInt(dadosLinha[0].toString());
    }

    private void abrirNovoMenu() {
        SwingUtilities.invokeLater(() ->
                new FormWindow("Novo Menu", 356, 554, new FormCadastroMenu()).setVisible(true)
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TabelaMenu().setVisible(true)
        );
    }
}