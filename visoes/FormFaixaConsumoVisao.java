package visoes;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.*;

import modeloFiles.FaixaConsumoFile;
import modelos.FaixaConsumoModelo;

public class FormFaixaConsumoVisao extends JPanel {

    public FormFaixaConsumoVisao(int tarifaId) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(450, 456));
        FormGerador form = new FormGerador(FaixaConsumoModelo.class);
        System.out.println("ewyyeyewyuewyuewyuewu: "+ tarifaId);
        form.adicionarAcaoBotao("Salvar", obj -> {
            FaixaConsumoModelo formDados = (FaixaConsumoModelo) obj;

            List<FaixaConsumoModelo> faixaConsumos = FaixaConsumoFile.instaciar().listar();
            
            int novoId = (faixaConsumos == null || faixaConsumos.isEmpty())? 1: faixaConsumos.getLast().getId() + 1;

            FaixaConsumoModelo novoFaixaConsumo = new FaixaConsumoModelo(
                novoId,
                formDados.getLimiteMaximo(),
                formDados.getPreco(),
                formDados.getLimiteMinimo(),
                formDados.getNome(),
                formDados.getDescricao(),
                formDados.getOrdem(),
                formDados.getDesconto(),
                formDados.isSocial(),
                formDados.getObservacoes(),
                tarifaId
            );
            novoFaixaConsumo.salvarDados();

            JOptionPane.showMessageDialog(this, novoFaixaConsumo.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormFaixaConsumoVisao(FaixaConsumoModelo dadosFaixaConsumo) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(450, 456));
        
        FormGerador form = new FormGerador(FaixaConsumoModelo.class, dadosFaixaConsumo, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            FaixaConsumoModelo formDados = (FaixaConsumoModelo) obj;

            FaixaConsumoModelo faixaConsumoEditado = new FaixaConsumoModelo(
                dadosFaixaConsumo.getId(),
                formDados.getLimiteMaximo(),
                formDados.getPreco(),
                formDados.getLimiteMinimo(),
                formDados.getNome(),
                formDados.getDescricao(),
                formDados.getOrdem(),
                formDados.getDesconto(),
                formDados.isSocial(),
                formDados.getObservacoes(),
                dadosFaixaConsumo.getTarifaId()
            );
            faixaConsumoEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, faixaConsumoEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormFaixaConsumoVisao(0).setVisible(true));
    }
}