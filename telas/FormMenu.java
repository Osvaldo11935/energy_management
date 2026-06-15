package telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.*;
import javax.swing.*;

import modeloFiles.MenuFile;
import models.MenuModelo;
public class FormMenu extends JPanel {
    public FormMenu() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(356, 554));
        FormGerador form = new FormGerador(MenuModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            MenuModelo menu = (MenuModelo) obj;

            List<MenuModelo> menus = new MenuFile(new MenuModelo()).listar();
            
            int novoId = (menus == null || menus.isEmpty())? 1: menus.getLast().getId() + 1;

            MenuModelo novoMenu = new MenuModelo(
                novoId,
                menu.getMenuPaiId(),
                menu.getCodigo(),
                menu.getNome(),
                menu.getDescricao(),
                menu.getIcone(),
                menu.getCaminhoClasse(),
                menu.getOrdem(),
                menu.getNivelMinimoAcesso()
            );
            novoMenu.salvarDados();
            JOptionPane.showMessageDialog(this, novoMenu.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormMenu(MenuModelo dadosMenu) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(356, 554));
        
        FormGerador form = new FormGerador(MenuModelo.class, dadosMenu, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            MenuModelo menu = (MenuModelo) obj;

            MenuModelo menuEditado = new MenuModelo(
                dadosMenu.getId(),
                menu.getMenuPaiId(),
                menu.getCodigo(),
                menu.getNome(),
                menu.getDescricao(),
                menu.getIcone(),
                menu.getCaminhoClasse(),
                menu.getOrdem(),
                menu.getNivelMinimoAcesso()
            );
            menuEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, menuEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormMenu().setVisible(true));
    }
}