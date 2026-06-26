package visoes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import modeloFiles.AtalhoFile;
import modelos.AtalhoModelo;
import modelos.UsuarioModelo;
import utils.Session;

public class FormAtalhoVisao  extends JPanel {
    public FormAtalhoVisao() {
        
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 300));
        FormGerador form = new FormGerador(AtalhoModelo.class);

        UsuarioModelo usuarioLogado = Session.getUsuario();

        form.adicionarAcaoBotao("Salvar", obj -> {
            AtalhoModelo formDados = (AtalhoModelo) obj;

            List<AtalhoModelo> atalhos = new AtalhoFile(new AtalhoModelo()).listar();
            
            int novoId = (atalhos == null || atalhos.isEmpty())? 1: atalhos.getLast().getId() + 1;

            AtalhoModelo novoAtalho = new AtalhoModelo(
                novoId,
                formDados.getMenuId(),
                formDados.getNome(),
                formDados.getDescricao(),
                formDados.getTeclado(),
                usuarioLogado.getId()
            );
            novoAtalho.salvarDados();

            JOptionPane.showMessageDialog(this, novoAtalho.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormAtalhoVisao(AtalhoModelo dadosAtalho) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 300));
        
        FormGerador form = new FormGerador(AtalhoModelo.class, dadosAtalho, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            AtalhoModelo formDados = (AtalhoModelo) obj;

            AtalhoModelo AtalhoEditado = new AtalhoModelo(
                dadosAtalho.getId(),
                formDados.getMenuId(),
                formDados.getNome(),
                formDados.getDescricao(),
                formDados.getTeclado(),
                dadosAtalho.getUsuarioId()
            );
            AtalhoEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, AtalhoEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormAtalhoVisao().setVisible(true));
    }
}
