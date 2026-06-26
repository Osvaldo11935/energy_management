package visoes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.*;
import javax.swing.*;
import modeloFiles.AreaDistribuicaoFile;
import modelos.AreaDistribuicaoModelo;

public class FormAreaDistribuicaoVisao   extends JPanel {
    public FormAreaDistribuicaoVisao() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(456, 387));

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
                areaDistribuicao.getNumeroClientes(),
                areaDistribuicao.getSubestacaoId()
            );
            novoAreaDistribuicao.salvarDados();

            JOptionPane.showMessageDialog(this, novoAreaDistribuicao.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormAreaDistribuicaoVisao(AreaDistribuicaoModelo dadosAreaDistribuicao) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(456, 387));
        
        FormGerador form = new FormGerador(AreaDistribuicaoModelo.class, dadosAreaDistribuicao, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            AreaDistribuicaoModelo areaDistribuicao = (AreaDistribuicaoModelo) obj;

            AreaDistribuicaoModelo areaDistribuicaoEditado = new AreaDistribuicaoModelo(
                dadosAreaDistribuicao.getId(),
                areaDistribuicao.getProvincia(),
                areaDistribuicao.getMunicipio(),
                areaDistribuicao.getComuna(),
                areaDistribuicao.getBairro(),
                areaDistribuicao.getNumeroClientes(),
                areaDistribuicao.getSubestacaoId()
            );
            areaDistribuicaoEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, areaDistribuicaoEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormAreaDistribuicaoVisao().setVisible(true));
    }
}