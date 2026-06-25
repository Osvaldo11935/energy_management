package telas.componentes;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class PainelCamposGrid extends JPanel {
    private final GridBagConstraints gbc;
    private int linhaAtual = 0;
    
    public PainelCamposGrid() {
        super(new GridBagLayout());
        setBackground(Color.WHITE);
        
        this.gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;
    }
    
    public void adicionarCampo(String label, String valor) {
        adicionarCampo(label, valor, null);
    }
    
    public void adicionarCampo(String label, String valor, Color corLabel) {
        gbc.gridx = 0;
        gbc.gridy = linhaAtual;
        gbc.weightx = 0.35;
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 13));
        lblLabel.setForeground(corLabel != null ? corLabel : new Color(85, 85, 85));
        add(lblLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.65;
        
        String valorExibir = (valor != null && !valor.isEmpty()) ? valor : "—";
        JLabel lblValor = new JLabel(valorExibir);
        lblValor.setFont(new Font("Arial", Font.PLAIN, 13));
        lblValor.setForeground(new Color(51, 51, 51));
        add(lblValor, gbc);
        
        linhaAtual++;
    }
    
    public void adicionarCampos(Map<String, String> campos) {
        campos.forEach(this::adicionarCampo);
    }
    
    public void limpar() {
        removeAll();
        linhaAtual = 0;
        revalidate();
        repaint();
    }
}