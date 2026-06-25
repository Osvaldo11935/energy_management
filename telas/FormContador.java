package telas;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.*;

import modeloFiles.ContadorFile;
import models.ContadorModelo;

public class FormContador extends JPanel {
    private Supplier<String> idClienteProvider;
    public FormContador(Supplier<String> idClienteProvider) {
        this.idClienteProvider = idClienteProvider;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(450, 456));
        FormGerador form = new FormGerador(ContadorModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            ContadorModelo formDados = (ContadorModelo) obj;

            List<ContadorModelo> contadors = ContadorFile.instaciar().listar();
            
            int novoId = (contadors == null || contadors.isEmpty())? 1: contadors.getLast().getId() + 1;

            String idCliente = this.idClienteProvider.get();

            ContadorModelo novoContador = new ContadorModelo(
                novoId,
                formDados.getTipoContador(),
                Integer.parseInt(idCliente),
                formDados.getDataInstalacao(),
                0.0
            );
            novoContador.salvarDados();

            JOptionPane.showMessageDialog(this, novoContador.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormContador(ContadorModelo dadosContador) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(450, 456));
        
        FormGerador form = new FormGerador(ContadorModelo.class, dadosContador, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            ContadorModelo formDados = (ContadorModelo) obj;

            ContadorModelo contadorEditado = new ContadorModelo(
                dadosContador.getId(),
                formDados.getTipoContador(),
                dadosContador.getClienteId(),
                formDados.getDataInstalacao(),
                0.0
            );
            contadorEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, contadorEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormContador(()-> "").setVisible(true));
    }
}