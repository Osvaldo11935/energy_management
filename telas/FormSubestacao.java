package telas;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import modeloFiles.SubestacaoFile;
import models.SubestacaoModelo;

public class FormSubestacao extends JPanel {
    public FormSubestacao() {

        setLayout(new BorderLayout());

        FormGerador form = new FormGerador(SubestacaoModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            SubestacaoModelo Subestacao = (SubestacaoModelo) obj;

            List<SubestacaoModelo> Subestacaos = new SubestacaoFile(new SubestacaoModelo()).listar();
            
            int novoId = (Subestacaos == null || Subestacaos.isEmpty())? 1: Subestacaos.getLast().getId() + 1;

            SubestacaoModelo novoSubestacao = new SubestacaoModelo(
                novoId,
                Subestacao.getCodigo(),
                Subestacao.getNome(),
                Subestacao.getLocalizacao(),
                Subestacao.getProvincia(),
                Subestacao.getMunicipio(),
                Subestacao.getCapacidade(),
                Subestacao.getTensaoNominal(),
                Subestacao.getUsuarioId(),
                Subestacao.getLatitude(),
                Subestacao.getLongitude(),
                Subestacao.getObservacoes()
            );
            novoSubestacao.salvarDados();

            JOptionPane.showMessageDialog(this, novoSubestacao.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormSubestacao(SubestacaoModelo dadosSubestacao) {

        setLayout(new BorderLayout());

        FormGerador form = new FormGerador(SubestacaoModelo.class, dadosSubestacao, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            SubestacaoModelo Subestacao = (SubestacaoModelo) obj;

            SubestacaoModelo SubestacaoEditado = new SubestacaoModelo(
                dadosSubestacao.getId(),
                Subestacao.getCodigo(),
                Subestacao.getNome(),
                Subestacao.getLocalizacao(),
                Subestacao.getProvincia(),
                Subestacao.getMunicipio(),
                Subestacao.getCapacidade(),
                Subestacao.getTensaoNominal(),
                Subestacao.getUsuarioId(),
                Subestacao.getLatitude(),
                Subestacao.getLongitude(),
                Subestacao.getObservacoes()
            );
            SubestacaoEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, SubestacaoEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormSubestacao().setVisible(true));
    }
}