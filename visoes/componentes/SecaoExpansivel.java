package visoes.componentes;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;


public class SecaoExpansivel extends JPanel {
    private final String titulo;
    private final JPanel conteudoPanel;
    private  JLabel iconeLabel;
    private boolean expandido;
    private Consumer<Boolean> onToggleListener;
    
    public SecaoExpansivel(String titulo) {
        this.titulo = titulo;
        this.expandido = false;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        

        JPanel header = criarHeader();
        add(header);

        this.conteudoPanel = new JPanel();
        conteudoPanel.setLayout(new BoxLayout(conteudoPanel, BoxLayout.Y_AXIS));
        conteudoPanel.setBackground(Color.WHITE);
        conteudoPanel.setVisible(false);
        add(conteudoPanel);
    }
    
    private JPanel criarHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tituloLabel.setForeground(new Color(52, 73, 94));
        
        iconeLabel = new JLabel("▶");
        iconeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        header.add(tituloLabel, BorderLayout.WEST);
        header.add(iconeLabel, BorderLayout.EAST);
        
        header.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                toggle();
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                header.setBackground(new Color(248, 248, 248));
                tituloLabel.setForeground(new Color(41, 128, 185));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                header.setBackground(Color.WHITE);
                tituloLabel.setForeground(new Color(52, 73, 94));
            }
        });
        
        return header;
    }
    
    public void toggle() {
        expandido = !expandido;
        conteudoPanel.setVisible(expandido);
        iconeLabel.setText(expandido ? "▼" : "▶");
        
        if (onToggleListener != null) {
            onToggleListener.accept(expandido);
        }
        
        revalidate();
        repaint();
    }
    
    public void setExpandido(boolean expandido) {
        if (this.expandido != expandido) {
            toggle();
        }
    }
    
    public void adicionarComponente(JComponent componente) {
        conteudoPanel.add(componente);
        conteudoPanel.add(Box.createVerticalStrut(10));
    }
    
    public void limparConteudo() {
        conteudoPanel.removeAll();
        conteudoPanel.revalidate();
        conteudoPanel.repaint();
    }
    
    public void setOnToggleListener(Consumer<Boolean> listener) {
        this.onToggleListener = listener;
    }
    
    public boolean isExpandido() {
        return expandido;
    }
}