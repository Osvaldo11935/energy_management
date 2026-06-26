package visoes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import modelos.PerfilModelo;

public class FormPerfilVisao  extends JPanel {
    public FormPerfilVisao() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 300));
        FormGerador form = new FormGerador(PerfilModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            PerfilModelo formDados = (PerfilModelo) obj;

            PerfilModelo novoPerfil = new PerfilModelo(
                formDados.getNome(),
                formDados.getDescricao()
            );
            novoPerfil.salvarDados();

            JOptionPane.showMessageDialog(this, novoPerfil.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormPerfilVisao(PerfilModelo dadosPerfil) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 300));
        
        FormGerador form = new FormGerador(PerfilModelo.class, dadosPerfil, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            PerfilModelo formDados = (PerfilModelo) obj;
            
            dadosPerfil.setDescricao(formDados.getNome());
            dadosPerfil.setDescricao(formDados.getDescricao());
            dadosPerfil.atualizarDados();

            JOptionPane.showMessageDialog(this, dadosPerfil.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPerfilVisao().setVisible(true));
    }
}
