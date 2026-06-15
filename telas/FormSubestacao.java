package telas;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import modeloFiles.SubestacaoFile;
import models.SubestacaoModelo;

public class FormSubestacao extends JPanel {
    public FormSubestacao() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(456, 628));

        FormGerador form = new FormGerador(SubestacaoModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            SubestacaoModelo formDados = (SubestacaoModelo) obj;

            List<SubestacaoModelo> subestacaos = new SubestacaoFile(new SubestacaoModelo()).listar();
            
            int novoId = (subestacaos == null || subestacaos.isEmpty())? 1: subestacaos.getLast().getId() + 1;

            SubestacaoModelo novoSubestacao = new SubestacaoModelo(
                novoId,
                formDados.getCodigo(),
                formDados.getNome(),
                formDados.getLocalizacao(),
                formDados.getProvincia(),
                formDados.getMunicipio(),
                formDados.getCapacidade(),
                formDados.getTensaoNominal(),
                formDados.getUsuarioId(),
                formDados.getLatitude(),
                formDados.getLongitude(),
                formDados.getObservacoes()
            );
            novoSubestacao.salvarDados();

            JOptionPane.showMessageDialog(this, novoSubestacao.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormSubestacao(SubestacaoModelo dadosSubestacao) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(456, 628));
        
        FormGerador form = new FormGerador(SubestacaoModelo.class, dadosSubestacao, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            SubestacaoModelo formDados = (SubestacaoModelo) obj;

            SubestacaoModelo subestacaoEditado = new SubestacaoModelo(
                dadosSubestacao.getId(),
                formDados.getCodigo(),
                formDados.getNome(),
                formDados.getLocalizacao(),
                formDados.getProvincia(),
                formDados.getMunicipio(),
                formDados.getCapacidade(),
                formDados.getTensaoNominal(),
                formDados.getUsuarioId(),
                formDados.getLatitude(),
                formDados.getLongitude(),
                formDados.getObservacoes()
            );
            subestacaoEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, subestacaoEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormSubestacao().setVisible(true));
    }
}