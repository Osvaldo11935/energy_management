package visoes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.*;

import modeloFiles.NotificacaoFile;
import modelos.NotificacaoModelo;

public class FormNotificacaoVisao extends JPanel {

    public FormNotificacaoVisao() {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(456, 456));
        FormGerador form = new FormGerador(NotificacaoModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            NotificacaoModelo formDados = (NotificacaoModelo) obj;

            List<NotificacaoModelo> notificacaos = NotificacaoFile.instaciar().listar();
            
            int novoId = (notificacaos == null || notificacaos.isEmpty())? 1: notificacaos.getLast().getId() + 1;
            
            

            NotificacaoModelo novoNotificacao = new NotificacaoModelo(
                novoId,
                formDados.getClienteId(),
                formDados.getAreaId(),
                formDados.getTitulo(),
                formDados.getMensagem(),
                formDados.getTipo()
            );

            novoNotificacao.salvarDados();

            JOptionPane.showMessageDialog(this, novoNotificacao.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public FormNotificacaoVisao(NotificacaoModelo dadosNotificacao) {

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(450, 456));
        
        FormGerador form = new FormGerador(NotificacaoModelo.class, dadosNotificacao, "Atualizar");

        form.adicionarAcaoBotao("Atualizar", obj -> {
            NotificacaoModelo formDados = (NotificacaoModelo) obj;

            NotificacaoModelo notificacaoEditado = new NotificacaoModelo(
                dadosNotificacao.getId(),
                formDados.getClienteId(),
                formDados.getAreaId(),
                formDados.getTitulo(),
                formDados.getMensagem(),
                formDados.getTipo()
            );
            notificacaoEditado.atualizarDados();
            JOptionPane.showMessageDialog(this, notificacaoEditado.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormNotificacaoVisao().setVisible(true));
    }
}