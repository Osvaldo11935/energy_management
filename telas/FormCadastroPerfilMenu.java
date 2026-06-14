package telas;

import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;
import modeloFiles.PerfilMenuFile;
import models.PerfilMenuModelo;

public class FormCadastroPerfilMenu extends JPanel{
   public FormCadastroPerfilMenu() {

        setLayout(new BorderLayout());

        FormGerador form = new FormGerador(PerfilMenuModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            PerfilMenuModelo formDados = (PerfilMenuModelo) obj;

            List<PerfilMenuModelo> perfilMenus = new PerfilMenuFile(new PerfilMenuModelo()).listar();
            
            int novoId = (perfilMenus == null || perfilMenus.isEmpty())? 1: perfilMenus.getLast().getId() + 1;

            PerfilMenuModelo novoPerfilMenu = new PerfilMenuModelo(
                novoId,
                formDados.getPerfilId(),
                formDados.getMenuId(),
                formDados.isPodeVisualizar(),
                formDados.isPodeCriar(),
                formDados.isPodeEditar(),
                formDados.isPodeEliminar()
            );
            novoPerfilMenu.salvarDados();
            JOptionPane.showMessageDialog(this, novoPerfilMenu.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormCadastroPerfilMenu(PerfilMenuModelo dadosPerfilMenu) {

        setLayout(new BorderLayout());

        FormGerador form = new FormGerador(PerfilMenuModelo.class, dadosPerfilMenu, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            PerfilMenuModelo formDados = (PerfilMenuModelo) obj;

            PerfilMenuModelo perfilMenuEditado = new PerfilMenuModelo(
                dadosPerfilMenu.getId(),
                formDados.getPerfilId(),
                formDados.getMenuId(),
                formDados.isPodeVisualizar(),
                formDados.isPodeCriar(),
                formDados.isPodeEditar(),
                formDados.isPodeEliminar()
            );
            perfilMenuEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, perfilMenuEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormCadastroPerfilMenu().setVisible(true));
    }
}
