package telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.*;
import java.util.function.*;
import javax.swing.*;
import modeloFiles.UsuarioFile;
import models.UsuarioModelo;

public class FormUsuario extends JPanel {
    private String idGerado;
    public FormUsuario(Consumer<String> aoSalvarCallback) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(456, 387));
        
        FormGerador form = new FormGerador(UsuarioModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            UsuarioModelo usuario = (UsuarioModelo) obj;

            List<UsuarioModelo> usuarios = new UsuarioFile(new UsuarioModelo()).listar();
            
            int novoId = (usuarios == null || usuarios.isEmpty())? 1: usuarios.getLast().getId() + 1;

            UsuarioModelo novoUsuario = new UsuarioModelo(
                novoId,
                usuario.getNomeUsuario(),
                usuario.getEmail(),
                usuario.getNumeroTelefone(),
                usuario.getPalavraPass(),
                usuario.getPerfilId()
            );
            novoUsuario.salvarDados();

            this.idGerado = String.valueOf(novoUsuario.getId());

            if (aoSalvarCallback != null) {
                aoSalvarCallback.accept(this.idGerado);
            }

            JOptionPane.showMessageDialog(this, novoUsuario.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormUsuario(UsuarioModelo dadosUsuario) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(456, 387));
        
        FormGerador form = new FormGerador(UsuarioModelo.class, dadosUsuario, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            UsuarioModelo usuario = (UsuarioModelo) obj;

            UsuarioModelo usuarioEditado = new UsuarioModelo(
                dadosUsuario.getId(),
                usuario.getNomeUsuario(),
                usuario.getEmail(),
                usuario.getNumeroTelefone(),
                usuario.getPalavraPass(),
                usuario.getPerfilId()
            );
            usuarioEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, usuarioEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormUsuario(id->{}).setVisible(true));
    }
}