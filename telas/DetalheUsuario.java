package telas;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.function.Supplier;
import javax.swing.*;
import modeloFiles.ClienteFile;
import modeloFiles.UsuarioFile;
import models.ClienteModelo;
import models.UsuarioModelo;
import utils.Session;

public class DetalheUsuario extends JFrame {

	private JTabbedPane tabs;
	private Supplier<String> idUsuarioProvider;
	private int usuarioId;
	private boolean ehCliente;

	public DetalheUsuario() {
		UsuarioModelo usuario = Session.getUsuario();
		this.usuarioId = usuario.getId();
		this.idUsuarioProvider = () -> String.valueOf(usuarioId);
		inicializar(usuario);
	}

	public DetalheUsuario(Supplier < String > idUsuarioProvider) {
		this.idUsuarioProvider = idUsuarioProvider;
		this.usuarioId = Integer.parseInt(idUsuarioProvider.get());
		UsuarioModelo usuario = UsuarioFile.instaciar().obterPorId(usuarioId);
		inicializar(usuario);
	}

	private void inicializar(UsuarioModelo usuario) {
		setTitle("Detalhes do Usuário");
		setSize(600, 730);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		tabs = new JTabbedPane();
		ehCliente = Session.getUsuario().ehCliente();
		montarAbas();
		add(tabs, BorderLayout.CENTER);
		if(!ehCliente) {
			add(criarPainelBotoes(), BorderLayout.SOUTH);
		}
	}

	private JPanel criarPainelBotoes() {
		JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(e -> abrirFormulario());
		painel.add(btnNovo);
		return painel;
	}

	private void abrirFormulario() {
		JFrame tela;
		if(tabs.getSelectedIndex() == 0) {
			tela = new FormWindow("Dados Pessoais", 456, 510, new FormPessoa(
				() -> String.valueOf(usuarioId)));
		} else {
			tela = new FormContrato(usuarioId);
		}
		tela.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				recarregar();
			}
		});
		tela.setVisible(true);
	}

	private void montarAbas() {
		tabs.removeAll();
		JPanel abaDados = new JPanel(new BorderLayout());
		DetalhePessoa dados = new DetalhePessoa();
		abaDados.add(dados.criarJanela(idUsuarioProvider), BorderLayout.CENTER);
		tabs.addTab("Dados Pessoais", abaDados);
		UsuarioModelo usuario = UsuarioFile.instaciar().obterPorId(usuarioId);
		if(usuario.ehCliente()) {
			List < ClienteModelo > clientes = new ClienteFile(new ClienteModelo()).buscarClientePorUsuarioId(usuarioId);
			JPanel abaContrato = new JPanel(new BorderLayout());
			if(clientes != null && !clientes.isEmpty()) {
				DetalheContrato contrato = new DetalheContrato();
				abaContrato.add(contrato.criarJanela(idUsuarioProvider), BorderLayout.CENTER);
			}
			tabs.addTab("Contrato", abaContrato);
		}
		tabs.revalidate();
		tabs.repaint();
	}

	private void recarregar() {
		montarAbas();
		tabs.revalidate();
		tabs.repaint();
	}
}