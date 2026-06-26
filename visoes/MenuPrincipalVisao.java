package visoes;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import utils.Session;
import modeloFiles.AtalhoFile;
import modeloFiles.ClienteFile;
import modeloFiles.NotificacaoFile;
import modeloFiles.PerfilMenuFile;
import modelos.AtalhoModelo;
import modelos.ClienteModelo;
import modelos.MenuModelo;
import modelos.NotificacaoModelo;
import modelos.PerfilMenuModelo;
import modelos.UsuarioModelo;
public class MenuPrincipalVisao extends JFrame {
	private UsuarioModelo usuario;
	private JMenuBar menuBar;
	private JPanel painelAtalhos;
	public MenuPrincipalVisao() {
		super("Sistema");

		if(!Session.estaLogado()) {
			dispose();
			SwingUtilities.invokeLater(() -> new FormLoginVisao().setVisible(true));
			return;
		}

		usuario = Session.getUsuario();
		setLayout(new BorderLayout());
		menuBar = new JMenuBar();
		painelAtalhos = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
		add(new JScrollPane(painelAtalhos), BorderLayout.CENTER);
		carregarMenus();
		setJMenuBar(menuBar);
		configurarAtalhosTeclado();
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public void atualizarMenus() {
		carregarMenus();
	}
	private void carregarAtalhos() {
		painelAtalhos.removeAll();
		List<AtalhoModelo> atalhos = AtalhoFile.instaciar().buscarAtalhoPorUsuarioId(usuario.getId());
		for(AtalhoModelo atalho: atalhos) {
			if(atalho.getMenu() == null) {
				continue;
			}
			criarAtalho(atalho);
		}
		painelAtalhos.revalidate();
		painelAtalhos.repaint();
	}
	private void configurarAtalhosTeclado() {
		JRootPane root = getRootPane();
		InputMap input = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap action = root.getActionMap();
		input.clear();
		List<AtalhoModelo> atalhos = AtalhoFile.instaciar().buscarAtalhoPorUsuarioId(usuario.getId());
		for(AtalhoModelo atalho: atalhos) {
			if(atalho.getMenu() == null) {
				continue;
			}
			String texto = atalho.getTeclado();
			KeyStroke tecla = converterTecla(texto);
			if(tecla == null) {
				continue;
			}
			String nome = "atalho_" + atalho.getId();
			input.put(tecla, nome);
			action.put(nome, new AbstractAction() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					abrirClasse(atalho.getMenu().getCaminhoClasse());
				}
			});
		}
	}
	private void criarAtalho(AtalhoModelo atalho) {
		MenuModelo menu = atalho.getMenu();
		JButton btn = new JButton(atalho.getNome());
		btn.setPreferredSize(new Dimension(100, 70));
		btn.setVerticalTextPosition(SwingConstants.BOTTOM);
		btn.setHorizontalTextPosition(SwingConstants.CENTER);
		btn.setFocusPainted(false);
		btn.setToolTipText(atalho.getDescricao());
		btn.setIcon(carregarIcone(menu.getIcone(), 38, 38));
		btn.addActionListener(e -> abrirClasse(menu.getCaminhoClasse()));
		painelAtalhos.add(btn);
	}
	private void carregarMenus() {
		menuBar.removeAll();
		List<PerfilMenuModelo> perfilMenus = new PerfilMenuFile(new PerfilMenuModelo()).buscarMenuPorPerfilId(usuario.getPerfilId());
		perfilMenus = perfilMenus.stream()
		                         .filter(pm -> pm.getMenu() != null)
								 .sorted(Comparator.comparingInt(pm -> pm.getMenu().getOrdem()))
								 .toList();

		List<MenuModelo> menus = perfilMenus.stream()
		                                       .map(PerfilMenuModelo::getMenu)
											   .filter(Objects::nonNull)
											   .toList();

		menus.stream()
		     .filter(m -> m.getMenuPaiId() == 0)
			 .forEach(menu -> menuBar.add(criarMenu(menu, menus)));

		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(criarMenuUsuario());
		carregarAtalhos();
		menuBar.revalidate();
		menuBar.repaint();
	}
	private JMenu criarMenuUsuario() {

		int totalNaoLidas = contarNotificacoesNaoLidas();

		JMenu menuUsuario = new JMenu(usuario.getNomeUsuario());

		menuUsuario.setIcon(carregarIcone("user.png", 20, 20));

		JMenuItem itemPerfil = new JMenuItem("Perfil");

		itemPerfil.setIcon(carregarIcone("perfil.png", 20, 20));

		itemPerfil.addActionListener(e ->
			new FormWindow("Perfil",   new DetalhePessoaVisao().criarJanela(() -> Integer.toString(usuario.getId()))).setVisible(true)
		);

		JMenuItem itemNotificacao = new JMenuItem("Notificações (" +totalNaoLidas +")");

		itemNotificacao.setIcon(carregarIcone("notificacao.png", 20, 20));

		itemNotificacao.addActionListener(e -> abrirClasse("telas.Notificacoes"));

		JMenuItem itemConfiguracao = new JMenuItem("Configuração");

		itemConfiguracao.setIcon(carregarIcone("config.png", 20, 20));

		itemConfiguracao.addActionListener(e -> abrirClasse("telas.Configuracao"));

		JMenuItem itemSair =
			new JMenuItem("Terminar Sessão");

		itemSair.setIcon(
			carregarIcone("logout.png", 20, 20)
		);

		itemSair.addActionListener(e -> {

			dispose();

			SwingUtilities.invokeLater(
				() -> new FormLoginVisao()
					.setVisible(true)
			);

		});

		menuUsuario.add(itemPerfil);

		if(usuario.ehCliente())
		  menuUsuario.add(itemNotificacao);

		menuUsuario.addSeparator();
		menuUsuario.add(itemConfiguracao);
		menuUsuario.addSeparator();
		menuUsuario.add(itemSair);

		return menuUsuario;
	}
	private JMenu criarMenu(MenuModelo menu, List<MenuModelo> todosMenus) {
		JMenu menuUI = new JMenu(menu.getNome());

		menuUI.setIcon(carregarIcone(menu.getIcone(), 20, 20));

		List<MenuModelo> filhos = todosMenus.stream()
		                                     .filter(m -> m.getMenuPaiId() == menu.getId())
											 .sorted(Comparator.comparingInt(MenuModelo::getOrdem))
											 .toList();
		if(filhos.isEmpty()) {
			if(menu.getCaminhoClasse() != null && !menu.getCaminhoClasse().isBlank()) {
				menuUI.addMenuListener(new MenuListener() {
					@Override
					public void menuSelected(MenuEvent e) {
						abrirClasse(menu.getCaminhoClasse());
					}
					@Override
					public void menuDeselected(MenuEvent e) {}
					@Override
					public void menuCanceled(MenuEvent e) {}
				});
			}
			return menuUI;
		}
		for(MenuModelo filho: filhos) {
			boolean filhoTemFilhos = todosMenus.stream()
			                                   .anyMatch(m -> m.getMenuPaiId() == filho.getId());
			if(filhoTemFilhos) {
				menuUI.add(criarMenu(filho, todosMenus));
			} else {
				JMenuItem item = new JMenuItem(filho.getNome());
				item.setIcon(carregarIcone(filho.getIcone(), 20, 20));
				item.addActionListener(e -> abrirClasse(filho.getCaminhoClasse()));
				menuUI.add(item);
			}
		}
		return menuUI;
	}
	private Icon carregarIcone(String nomeIcone, int largura, int altura) {
		try {
			if(nomeIcone == null || nomeIcone.isBlank()) {
				return null;
			}
			URL url = getClass().getResource("/image/" + nomeIcone);
			if(url == null) {
				return null;
			}
			Image imagem = new ImageIcon(url).getImage()
			                                 .getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
			return new ImageIcon(imagem);
		} catch (Exception ex) {
			return null;
		}
	}
	private void abrirClasse(String caminho) {
		try {
			Class <?> classe = Class.forName(caminho);
			Object obj = classe.getDeclaredConstructor().newInstance();
			if(obj instanceof JFrame frame) {
				frame.setLocationRelativeTo(this);
				frame.setVisible(true);
			} else if(obj instanceof JDialog dialog) {
				dialog.setLocationRelativeTo(this);
				dialog.setVisible(true);
			} else if(obj instanceof JPanel panel) {
				JFrame frame = new JFrame();
				frame.setContentPane(panel);
				frame.pack();
				frame.setLocationRelativeTo(this);
				frame.setVisible(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Erro ao abrir:\n" + caminho);
		}
	}
	private KeyStroke converterTecla(String valor) {
		if(valor == null || valor.isBlank()) {
			return null;
		}
		valor = valor.replace(" + ", " ")
		             .replace("Ctrl", "ctrl")
					 .replace("Alt", "alt")
					 .replace("Shift", "shift")
					 .trim();
		return KeyStroke.getKeyStroke(valor);
	}
	private int contarNotificacoesNaoLidas() {
		int total = 0;

		List<ClienteModelo> clientes = ClienteFile.instaciar().buscarClientePorUsuarioId(usuario.getId());

		for (ClienteModelo cliente : clientes) {

			List<NotificacaoModelo> notificacoes = NotificacaoFile.instaciar()
					                                              .buscarPorClienteId(cliente.getId());

			for (NotificacaoModelo n :notificacoes) {
				if (!n.isLida()) {
					total++;
				}
			}
		}

		return total;
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MenuPrincipalVisao::new);
	}
}