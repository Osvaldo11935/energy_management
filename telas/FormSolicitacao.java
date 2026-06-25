package telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import modeloFiles.SolicitacaoFile;
import models.SolicitacaoModelo;
import models.UsuarioModelo;
import utils.Session;

public class FormSolicitacao extends JPanel {
    public FormSolicitacao() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(456, 396));

        FormGerador form = new FormGerador(SolicitacaoModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            UsuarioModelo usuarioLogado = Session.getUsuario();
            SolicitacaoModelo formDados = (SolicitacaoModelo) obj;
            
            List<SolicitacaoModelo> solicitacoes = new SolicitacaoFile(new SolicitacaoModelo()).listar();
            
            int novoId = (solicitacoes == null || solicitacoes.isEmpty())? 1: solicitacoes.getLast().getId() + 1;
     
            SolicitacaoModelo novoSolicitacao = new SolicitacaoModelo(
                novoId,
                usuarioLogado.getId(),
                formDados.getContratoId(),
                formDados.getTipoSolicitacao(),
                formDados.getDescricao(),
                formDados.getPrioridade(),
                formDados.getTecnicoResponsavelId(),
                0
            );
            novoSolicitacao.salvarDados();

            JOptionPane.showMessageDialog(this, novoSolicitacao.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormSolicitacao(SolicitacaoModelo dadosSolicitacao) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(456, 396));
        
        FormGerador form = new FormGerador(SolicitacaoModelo.class, dadosSolicitacao, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            SolicitacaoModelo formDados = (SolicitacaoModelo) obj;

            SolicitacaoModelo solicitacaoEditado = new SolicitacaoModelo(
                dadosSolicitacao.getId(),
                dadosSolicitacao.getUsuarioId(),
                formDados.getContratoId(),
                formDados.getTipoSolicitacao(),
                formDados.getDescricao(),
                formDados.getPrioridade(),
                formDados.getTecnicoResponsavelId(),
                0
            );
            solicitacaoEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, solicitacaoEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormSolicitacao().setVisible(true));
    }
}