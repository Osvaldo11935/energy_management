package telas;

import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;
import modeloFiles.AreaDistribuicaoFile;
import models.AreaDistribuicaoModelo;

public class FormAreaDistribuicao   extends JPanel {
    public FormAreaDistribuicao() {

        setLayout(new BorderLayout());

        FormGerador form = new FormGerador(AreaDistribuicaoModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            AreaDistribuicaoModelo areaDistribuicao = (AreaDistribuicaoModelo) obj;

            List<AreaDistribuicaoModelo> areaDistribuicaos = new AreaDistribuicaoFile(new AreaDistribuicaoModelo()).listar();
            
            int novoId = (areaDistribuicaos == null || areaDistribuicaos.isEmpty())? 1: areaDistribuicaos.getLast().getId() + 1;

            AreaDistribuicaoModelo novoAreaDistribuicao = new AreaDistribuicaoModelo(
                novoId,
                areaDistribuicao.getProvincia(),
                areaDistribuicao.getMunicipio(),
                areaDistribuicao.getComuna(),
                areaDistribuicao.getBairro(),
                areaDistribuicao.getCodigoPostal(),
                areaDistribuicao.getSubestacaoId()
            );
            novoAreaDistribuicao.salvarDados();

            JOptionPane.showMessageDialog(this, novoAreaDistribuicao.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormAreaDistribuicao(AreaDistribuicaoModelo dadosAreaDistribuicao) {

        setLayout(new BorderLayout());

        FormGerador form = new FormGerador(AreaDistribuicaoModelo.class, dadosAreaDistribuicao, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            AreaDistribuicaoModelo areaDistribuicao = (AreaDistribuicaoModelo) obj;

            AreaDistribuicaoModelo areaDistribuicaoEditado = new AreaDistribuicaoModelo(
                dadosAreaDistribuicao.getId(),
                areaDistribuicao.getProvincia(),
                areaDistribuicao.getMunicipio(),
                areaDistribuicao.getComuna(),
                areaDistribuicao.getBairro(),
                areaDistribuicao.getCodigoPostal(),
                areaDistribuicao.getSubestacaoId()
            );
            areaDistribuicaoEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, areaDistribuicaoEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormAreaDistribuicao().setVisible(true));
    }
}