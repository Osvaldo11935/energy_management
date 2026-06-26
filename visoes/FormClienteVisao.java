package visoes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.*;
import modeloFiles.ClienteFile;
import modelos.ClienteModelo;

public class FormClienteVisao extends JPanel {
    private Supplier<String> idUsuarioProvider;
    public FormClienteVisao(Supplier<String> idUsuarioProvider, Consumer<String> aoSalvarCallback) {
        this.idUsuarioProvider = idUsuarioProvider;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(450, 456));
        FormGerador form = new FormGerador(ClienteModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            ClienteModelo formDados = (ClienteModelo) obj;

            List<ClienteModelo> clientes = new ClienteFile(new ClienteModelo()).listar();
            
            int novoId = (clientes == null || clientes.isEmpty())? 1: clientes.getLast().getId() + 1;

            String idUsuario = this.idUsuarioProvider.get();
            ClienteModelo novoCliente = new ClienteModelo(
                novoId,
                Integer.parseInt(idUsuario),
                formDados.getTipoCliente(),
                formDados.getTipoContrato(),
                formDados.getAreaDistribuicaoId(),
                0.0,
                0.0,
                0.0,
                formDados.getObservacoes()
            );

            novoCliente.salvarDados();
            String idGerado = String.valueOf(novoCliente.getId());

            if (aoSalvarCallback != null) {
                aoSalvarCallback.accept(idGerado);
            }

            JOptionPane.showMessageDialog(this, novoCliente.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormClienteVisao(ClienteModelo dadosCliente) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(450, 456));
        
        FormGerador form = new FormGerador(ClienteModelo.class, dadosCliente, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            ClienteModelo formDados = (ClienteModelo) obj;

            ClienteModelo clienteEditado = new ClienteModelo(
                dadosCliente.getId(),
                formDados.getUsuarioId(),
                formDados.getTipoContrato(),
                formDados.getTipoCliente(),
                formDados.getAreaDistribuicaoId(),
                0.0,
                0.0,
                0.0,
                formDados.getObservacoes()
            );
            clienteEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, clienteEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormClienteVisao(()-> "", id ->{}).setVisible(true));
    }
}