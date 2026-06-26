package visoes.componentes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JanelaDetalheBuilder {
    private final String titulo;
    private final JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
    private final JPanel painelConteudo = new JPanel();
    private final List<SecaoConfig> secoes = new ArrayList<>();
    private Consumer<JPanel> botaoPersonalizado;
    private int largura = 550;
    private int altura = 500;
    private String corPrimaria = "#2980b9";
    
    public JanelaDetalheBuilder(String titulo) {
        this.titulo = titulo;
        painelConteudo.setLayout(new BoxLayout(painelConteudo, BoxLayout.Y_AXIS));
        painelConteudo.setBackground(Color.WHITE);
    }
    
    public JanelaDetalheBuilder setTamanho(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        return this;
    }
    
    public JanelaDetalheBuilder adicionarSecao(String id, String titulo, boolean expandidoPorPadrao, Consumer<JPanel> conteudoProvider) {
        secoes.add(new SecaoConfig(id, titulo, expandidoPorPadrao, conteudoProvider));
        return this;
    }
    
    public JanelaDetalheBuilder adicionarBotoesPadrao(Consumer<Void> onEditar, Runnable onFechar) {
        this.botaoPersonalizado = panel -> {
            JButton btnEditar = criarBotaoPadrao("Editar", new Color(52, 152, 219));
            JButton btnFechar = criarBotaoPadrao("Fechar", new Color(231, 76, 60));
            btnEditar.addActionListener(e -> onEditar.accept(null));
            panel.add(btnEditar);
            if(onFechar != null){
                btnFechar.addActionListener(e -> onFechar.run());
                panel.add(btnFechar);
            }
        };
        return this;
    }
    
    public JanelaDetalheBuilder adicionarTitulo(String titulo) {
        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        labelTitulo.setForeground(Color.decode(corPrimaria));
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        painelConteudo.add(labelTitulo);
        return this;
    }
    public JPanel construirPainel() {

        GerenciadorSecoes gerenciador = new GerenciadorSecoes();

        for (SecaoConfig config : secoes) {

            SecaoExpansivel secao = new SecaoExpansivel(config.titulo);

            secao.setExpandido(config.expandidoPorPadrao);

            JPanel containerConteudo =
                new JPanel(new BorderLayout());

            containerConteudo.setBackground(Color.WHITE);

            containerConteudo.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 10, 20)
            );

            config.conteudoProvider.accept(containerConteudo);

            secao.adicionarComponente(containerConteudo);

            painelConteudo.add(secao);

            painelConteudo.add(Box.createVerticalStrut(5));

            gerenciador.registrarSecao(
                config.id,
                config.expandidoPorPadrao
            );
        }

        JScrollPane scrollPane =
            new JScrollPane(painelConteudo);

        scrollPane.setBorder(null);

        scrollPane.getViewport().setBackground(Color.WHITE);

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        painelPrincipal.removeAll();

        painelPrincipal.add(
            scrollPane,
            BorderLayout.CENTER
        );

        if (botaoPersonalizado != null) {

            JPanel painelBotoes =
                new JPanel(
                    new FlowLayout(
                        FlowLayout.CENTER,
                        10,
                        10
                    )
                );

            botaoPersonalizado.accept(painelBotoes);

            painelPrincipal.add(
                painelBotoes,
                BorderLayout.SOUTH
            );
        }

        painelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(
                20,
                20,
                20,
                20
            )
        );

        return painelPrincipal;
    }
    public JFrame construir() {

        JFrame frame = new JFrame(titulo);

        frame.setSize(largura, altura);

        frame.setLocationRelativeTo(null);

        frame.setContentPane(construirPainel());

        return frame;
    }
    public JanelaDetalheBuilder adicionarBotoesSecao(JPanel secao, BotaoSecao... botoes) {
        JPanel painelBotoes =
            new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        painelBotoes.setOpaque(false);

        for (BotaoSecao b : botoes) {
            painelBotoes.add(b.criarBotao());
        }

        secao.add(painelBotoes, BorderLayout.SOUTH);

        return this;
    }
    private JButton criarBotaoPadrao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setPreferredSize(new Dimension(120, 35));
        return botao;
    }
    
    private static class SecaoConfig {
        final String id;
        final String titulo;
        final boolean expandidoPorPadrao;
        final Consumer<JPanel> conteudoProvider;
        
        SecaoConfig(String id, String titulo, boolean expandidoPorPadrao, Consumer<JPanel> conteudoProvider) {
            this.id = id;
            this.titulo = titulo;
            this.expandidoPorPadrao = expandidoPorPadrao;
            this.conteudoProvider = conteudoProvider;
        }
    }
    public void aplicarAoFrame(JFrame frame) {
        frame.setTitle(titulo);
        frame.setSize(largura, altura);
        frame.setLocationRelativeTo(null);
        
        JScrollPane scrollPane = new JScrollPane(painelConteudo);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        if (botaoPersonalizado != null) {
            JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            botaoPersonalizado.accept(painelBotoes);
            painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        }
        
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.setContentPane(painelPrincipal);
    }
}
