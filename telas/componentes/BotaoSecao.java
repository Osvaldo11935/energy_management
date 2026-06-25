package telas.componentes;

import javax.swing.*;
import java.awt.event.ActionListener;

public class BotaoSecao {

    private final String texto;
    private final ActionListener acao;

    public BotaoSecao(String texto, ActionListener acao) {
        this.texto = texto;
        this.acao = acao;
    }

    public JButton criarBotao() {
        JButton botao = new JButton(texto);

        if (acao != null) {
            botao.addActionListener(acao);
        }

        return botao;
    }
}