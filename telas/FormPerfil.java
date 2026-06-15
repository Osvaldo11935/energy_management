package telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import modeloFiles.PerfilFile;
import models.PerfilModelo;

public class FormPerfil  extends JPanel {
    public FormPerfil() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 300));
        FormGerador form = new FormGerador(PerfilModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            PerfilModelo formDados = (PerfilModelo) obj;

            List<PerfilModelo> perfils = new PerfilFile(new PerfilModelo()).listar();
            
            int novoId = (perfils == null || perfils.isEmpty())? 1: perfils.getLast().getId() + 1;

            PerfilModelo novoPerfil = new PerfilModelo(
                novoId,
                formDados.getNome(),
                formDados.getDescricao()
            );
            novoPerfil.salvarDados();

            JOptionPane.showMessageDialog(this, novoPerfil.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormPerfil(PerfilModelo dadosPerfil) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 300));
        
        FormGerador form = new FormGerador(PerfilModelo.class, dadosPerfil, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            PerfilModelo formDados = (PerfilModelo) obj;

            PerfilModelo perfilEditado = new PerfilModelo(
                dadosPerfil.getId(),
                formDados.getNome(),
                formDados.getDescricao()
            );
            perfilEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, perfilEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPerfil().setVisible(true));
    }
}
