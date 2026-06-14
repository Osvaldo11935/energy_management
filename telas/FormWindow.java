package telas;

import javax.swing.*;

public class FormWindow extends JFrame {

    public FormWindow(String titulo, JPanel formulario) {

        setTitle(titulo);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(formulario);
    }
    
    public FormWindow(String titulo, int largura, int altura, JPanel formulario) {

        setTitle(titulo);
        setSize(largura, altura);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(formulario);
    }
}