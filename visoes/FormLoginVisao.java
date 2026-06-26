package visoes;

import java.awt.*;
import javax.swing.*;

import modelos.LoginModelo;
import modelos.UsuarioModelo;
import utils.Session;

public class FormLoginVisao extends JFrame {

    public FormLoginVisao() {

        setTitle("Login");
        setSize(380, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(45, 62, 80));
        JLabel titulo = new JLabel("Acesso ao Sistema");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titulo);

        FormGerador form = new FormGerador(LoginModelo.class, null, "Entrar");

        form.adicionarAcaoBotao("Entrar", obj -> {

            LoginModelo login = (LoginModelo) obj;

            UsuarioModelo usuario = login.login();
            
            if (usuario != null) {
                Session.setUsuario(usuario);
                SwingUtilities.invokeLater(() -> {new MenuPrincipalVisao().setVisible(true);});
                this.dispose(); 
            } else {
                JOptionPane.showMessageDialog(this,
                        "Email ou senha inválidos");
            }
        });

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        centerPanel.add(form, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormLoginVisao().setVisible(true));
    }
}