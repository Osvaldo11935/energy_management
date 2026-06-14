package telas;

import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;

import modeloFiles.MenuFile;
import models.MenuModelo;
public class FormCadastroMenu extends JPanel {
    public FormCadastroMenu() {

        setLayout(new BorderLayout());

        FormGerador form = new FormGerador(MenuModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            MenuModelo Menu = (MenuModelo) obj;

            List<MenuModelo> Menus = new MenuFile(new MenuModelo()).listar();
            
            int novoId = (Menus == null || Menus.isEmpty())? 1: Menus.getLast().getId() + 1;

            MenuModelo novoMenu = new MenuModelo(
                novoId,
                Menu.getMenuPaiId(),
                Menu.getCodigo(),
                Menu.getNome(),
                Menu.getDescricao(),
                Menu.getIcone(),
                Menu.getCaminhoClasse(),
                Menu.getOrdem(),
                Menu.getNivelMinimoAcesso()
            );
            novoMenu.salvarDados();
            JOptionPane.showMessageDialog(this, novoMenu.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormCadastroMenu(MenuModelo dadosMenu) {

        setLayout(new BorderLayout());

        FormGerador form = new FormGerador(MenuModelo.class, dadosMenu, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            MenuModelo Menu = (MenuModelo) obj;

            MenuModelo MenuEditado = new MenuModelo(
                dadosMenu.getId(),
                Menu.getMenuPaiId(),
                Menu.getCodigo(),
                Menu.getNome(),
                Menu.getDescricao(),
                Menu.getIcone(),
                Menu.getCaminhoClasse(),
                Menu.getOrdem(),
                Menu.getNivelMinimoAcesso()
            );
            MenuEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, MenuEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormCadastroMenu().setVisible(true));
    }
}