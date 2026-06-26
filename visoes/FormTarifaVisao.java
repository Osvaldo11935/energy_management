package visoes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import modeloFiles.TarifaFile;
import modelos.TarifaModelo;

public class FormTarifaVisao  extends JPanel {
    public FormTarifaVisao() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(456, 348));
        FormGerador form = new FormGerador(TarifaModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            TarifaModelo formDados = (TarifaModelo) obj;

            List<TarifaModelo> tarifas = new TarifaFile(new TarifaModelo()).listar();
            
            int novoId = (tarifas == null || tarifas.isEmpty())? 1: tarifas.getLast().getId() + 1;
            TarifaModelo novoTarifa = new TarifaModelo(
                novoId,
                formDados.getNomeTarifa(),
                formDados.getPrecoKwh(),
                formDados.getTaxaFixa(),
                formDados.getMultaAtraso(),
                formDados.getDataVigor()
            );

            novoTarifa.salvarDados();

            JOptionPane.showMessageDialog(this, novoTarifa.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormTarifaVisao(TarifaModelo dadosTarifa) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 300));
        
        FormGerador form = new FormGerador(TarifaModelo.class, dadosTarifa, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            TarifaModelo formDados = (TarifaModelo) obj;

            TarifaModelo tarifaEditado = new TarifaModelo(
                dadosTarifa.getId(),
                formDados.getNomeTarifa(),
                formDados.getPrecoKwh(),
                formDados.getTaxaFixa(),
                formDados.getMultaAtraso(),
                formDados.getDataVigor()
            );
            tarifaEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, tarifaEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormTarifaVisao().setVisible(true));
    }
}
