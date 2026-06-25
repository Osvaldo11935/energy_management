package telas;

import javax.swing.*;

import modeloFiles.UsuarioFile;
import models.UsuarioModelo;

import java.awt.*;

public class FormContrato extends JFrame {

    private CardLayout cardLayout;
    private JPanel painelCards;

    private JButton btnAnterior;
    private JButton btnProximo;
    private JButton btnFinalizar;

    private int passoAtual = 0;
    private int totalPassos = 2;

    private final int[] alturas = {450, 300};

    private String clienteId;
    private int usuarioId;

    public FormContrato(int usuarioId) {
        this.usuarioId = usuarioId;
        setTitle("Cadastro Completo");
        setSize(456, alturas[0]);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cardLayout = new CardLayout();
        painelCards = new JPanel(cardLayout);

        painelCards.add(new FormCliente(() -> Integer.toString(usuarioId) ,  id -> {
            clienteId = id;
            configurarProximosPassos();
        }), "PASSO_1");

        btnAnterior = new JButton("Anterior");
        btnProximo = new JButton("Próximo");
        btnFinalizar = new JButton("Finalizar");

        btnAnterior.setEnabled(false);
        btnFinalizar.setVisible(false);

        btnAnterior.addActionListener(e -> voltar());
        btnProximo.addActionListener(e -> avancar());
        btnFinalizar.addActionListener(e -> finalizar());

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        rodape.add(btnAnterior);
        rodape.add(btnProximo);
        rodape.add(btnFinalizar);

        add(painelCards, BorderLayout.CENTER);
        add(rodape, BorderLayout.SOUTH);

        ajustarAltura();
    }

    private void configurarProximosPassos() {

        UsuarioModelo usuario =
                new UsuarioFile(new UsuarioModelo())
                        .obterPorId(usuarioId);

        if (usuario.ehCliente()) {
            painelCards.add(new FormContador(() -> clienteId),"PASSO_2");
            totalPassos = 2;

        } 
    }

    private void avancar() {
        if (passoAtual == 0 && (clienteId == null || clienteId.isBlank())) {
            JOptionPane.showMessageDialog(this,"Primeiro deve concluir o cadastro do contrato.");
            return;
        }
        if (passoAtual < totalPassos - 1) {

            passoAtual++;

            cardLayout.next(painelCards);

            atualizarBotoes();
        }
    }

    private void voltar() {

        if (passoAtual > 0) {

            passoAtual--;

            cardLayout.previous(painelCards);

            atualizarBotoes();
        }
    }

    private void atualizarBotoes() {

        btnAnterior.setEnabled(passoAtual > 0);

        boolean ultimoPasso = passoAtual == totalPassos - 1;

        btnProximo.setVisible(!ultimoPasso);
        btnFinalizar.setVisible(ultimoPasso);

        ajustarAltura();
    }

    private void ajustarAltura() {
        int altura = alturas[Math.min(passoAtual, alturas.length - 1)];
        setSize(getWidth(), altura);
        setLocationRelativeTo(null);
    }

    private void finalizar() {
        JOptionPane.showMessageDialog(this, "Cadastro concluído! ID do cliente: " + clienteId);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->new FormContrato(1).setVisible(true));
    }
}