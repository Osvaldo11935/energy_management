package telas;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import modeloFiles.PerfilFile;
import models.PerfilModelo;

public class FormCadastroPerfil extends FormWindow {
    public FormCadastroPerfil()
    {
         super("Perfil", 300, 300, criarFormulario());
    }
    private static JPanel criarFormulario()
    {
          FormGerador form = new FormGerador(PerfilModelo.class);

          form.adicionarAcaoBotao("Salvar", obj ->{
               PerfilModelo formDados = (PerfilModelo) obj;
               
               List<PerfilModelo> perfis = new PerfilFile(new PerfilModelo()).listar();

               int novoId = (perfis == null || perfis.isEmpty())? 1: perfis.getLast().getId() + 1;

               PerfilModelo novoPerfil = new PerfilModelo(
                    novoId,
                    formDados.getNome(),
                    formDados.getDescricao()
               );
               
               novoPerfil.salvarDados();

               JOptionPane.showMessageDialog(new FormCadastroPerfil(), novoPerfil.toString());
          });

          return form;
    }
    public static void main(String args[])
    {
         SwingUtilities.invokeLater(()->{new FormCadastroPerfil().setVisible(true);});
    }
}
