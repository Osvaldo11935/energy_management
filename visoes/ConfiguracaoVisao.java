package visoes;

import javax.swing. * ;

import modelos.UsuarioModelo;
import utils.Session;
import visoes.componentes.JanelaDetalheBuilder;

public class ConfiguracaoVisao extends JPanel {

  public ConfiguracaoVisao() {

    UsuarioModelo usuarioLogado = Session.getUsuario();

    if (usuarioLogado == null) {

      JOptionPane.showMessageDialog(
      null, "Usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);

      return;
    }

    try {

      JanelaDetalheBuilder builder = new JanelaDetalheBuilder("Configurações")
                                        .setTamanho(550, 500)
                                        .adicionarTitulo("CONFIGURAÇÕES")
                                        .adicionarSecao("dadosAtalhos", "CONFIGURAR ATALHOS", true, 
                                        panel ->{panel.add(new TabelaAtalhoVisao());});

      setLayout(new java.awt.BorderLayout());

      add(builder.construirPainel(), java.awt.BorderLayout.CENTER);

    } catch(Exception e) {

      JOptionPane.showMessageDialog(
      null, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
  }
}