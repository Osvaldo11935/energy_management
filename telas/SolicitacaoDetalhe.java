package telas;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import enums.EstadoSolicitacao;
import modeloFiles.SolicitacaoFile;
import models.SolicitacaoModelo;

public class SolicitacaoDetalhe extends JPanel {
	private SolicitacaoModelo solicitacao;
	private JPanel painelRespostas;
	private JTextArea txtResposta;
	private JButton btnResponder;

	public SolicitacaoDetalhe(SolicitacaoModelo solicitacao) {
		this.solicitacao = solicitacao;
		setLayout(new BorderLayout());
		add(criarTopo(), BorderLayout.NORTH);
		add(criarCentro(), BorderLayout.CENTER);
		add(criarRodape(), BorderLayout.SOUTH);
		carregarRespostas();
	}
	private JPanel criarTopo() {
		JPanel p = new JPanel(new GridLayout(5, 1));
		p.add(new JLabel("Tipo: " + solicitacao.getTipoSolicitacao()));
		p.add(new JLabel("Prioridade: " + solicitacao.getPrioridade()));
		p.add(new JLabel("Status: " + solicitacao.getStatus()));
		p.add(new JLabel("Data: " + solicitacao.getDataAbertura()));
		p.add(new JLabel("Nome do Técnico: " + solicitacao.getTecnicoResponsavel().getNomeUsuario()));
		p.add(new JLabel("Nº de Telefone do Técnico: " + solicitacao.getTecnicoResponsavel().getNumeroTelefone()));
		p.add(new JLabel("Email do Técnico: " + solicitacao.getTecnicoResponsavel().getEmail()));
		return p;
	}
	private JScrollPane criarCentro() {
		JPanel conteudo = new JPanel();
		conteudo.setLayout(new BoxLayout(conteudo, BoxLayout.Y_AXIS));
		JTextArea descricao = new JTextArea(solicitacao.getDescricao().toString());
		descricao.setEditable(false);
		conteudo.add(descricao);
		conteudo.add(new JLabel("Respostas"));
		painelRespostas = new JPanel();
		painelRespostas.setLayout(new BoxLayout(painelRespostas, BoxLayout.Y_AXIS));
		conteudo.add(painelRespostas);
		return new JScrollPane(conteudo);
	}
	private JPanel criarRodape() {
		JPanel p = new JPanel(new BorderLayout());
		txtResposta = new JTextArea(4, 40);
		btnResponder = new JButton("Responder");
		btnResponder.addActionListener(e -> responder());
		p.add(new JScrollPane(txtResposta), BorderLayout.CENTER);
		p.add(btnResponder, BorderLayout.EAST);
		return p;
	}
	private void carregarRespostas() {
		painelRespostas.removeAll();
		List <SolicitacaoModelo> respostas = SolicitacaoFile.instaciar().buscarPorSolicitacaoPaiId(solicitacao.getId());
		for(SolicitacaoModelo r: respostas) {
			JTextArea item = new JTextArea(r.getDescricao().toString());
			item.setEditable(false);
			item.setBorder(BorderFactory.createTitledBorder(r.getDataAbertura().toString()));
			painelRespostas.add(item);
		}
		repaint();
		revalidate();
	}
	private void responder() {

		if(txtResposta.getText().trim().isEmpty()) {
			return;
		}

        List<SolicitacaoModelo> solicitacoes = new SolicitacaoFile(new SolicitacaoModelo()).listar();
            
        int novoId = (solicitacoes == null || solicitacoes.isEmpty())? 1: solicitacoes.getLast().getId() + 1;

		SolicitacaoModelo resposta = new SolicitacaoModelo(
		    novoId,
            solicitacao.getUsuarioId(),
            solicitacao.getContratoId(),
            solicitacao.getTipoSolicitacao(),
            txtResposta.getText(),
            solicitacao.getPrioridade(),
            solicitacao.getTecnicoResponsavelId(),
            solicitacao.getId() 
		);

		resposta.setStatus(EstadoSolicitacao.RESPONDIDA.toString());

        resposta.salvarDados();

		txtResposta.setText("");
		carregarRespostas();
	}
}