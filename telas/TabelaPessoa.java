package telas;

import models.PessoaModelo;
import modeloFiles.PessoaFile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Supplier;

public class TabelaPessoa extends JFrame {
    private Supplier<String> idUsuarioProvider;
    private JPanel painelDetalhe;
    private JPanel painelConteudo;
    private boolean dadosPessoaisExpandido = true;
    private boolean filiacaoExpandido = false;
    private boolean documentosExpandido = false;
    private boolean enderecoExpandido = false;
    
    public TabelaPessoa(Supplier<String> idUsuarioProvider) {

        this.idUsuarioProvider = idUsuarioProvider;

        setTitle("Detalhes da Pessoa");
        setSize(550, 500);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Criar painel principal com padding
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Criar painel de detalhe com GridBagLayout para melhor organização
        painelDetalhe = new JPanel(new BorderLayout());
        painelConteudo = new JPanel();
        painelConteudo.setLayout(new BoxLayout(painelConteudo, BoxLayout.Y_AXIS));
        painelConteudo.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(painelConteudo);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(Color.white);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        painelDetalhe.add(scrollPane, BorderLayout.CENTER);
        
        // Carregar e exibir os dados
        carregarDetalhes();
        
        // Botão para voltar/editar
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton btnEditar = criarBotao("Editar", new Color(52, 152, 219));
        JButton btnFechar = criarBotao("Fechar", new Color(231, 76, 60));
        
        btnEditar.addActionListener(e -> editarPessoa());
        btnFechar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnFechar);
        
        painelPrincipal.add(painelDetalhe, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private void carregarDetalhes() {
        painelConteudo.removeAll();
        
        String idUsuario = idUsuarioProvider.get();
        
        if (idUsuario == null || idUsuario.isEmpty()) {
            exibirMensagemErro("ID do usuário não encontrado!");
            return;
        }
        
        try {
            PessoaFile arquivo = new PessoaFile(new PessoaModelo());
            List<PessoaModelo> pessoas = arquivo.listar();
            
            PessoaModelo pessoa = pessoas.stream()
                .findFirst()
                .orElse(null);
            
            if (pessoa == null) {
                exibirMensagemErro("Nenhuma informação encontrada para este usuário!");
                return;
            }
            
            // Título principal
            JLabel tituloPrincipal = new JLabel("INFORMAÇÕES PESSOAIS");
            tituloPrincipal.setFont(new Font("Arial", Font.BOLD, 20));
            tituloPrincipal.setForeground(new Color(41, 128, 185));
            tituloPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
            tituloPrincipal.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            painelConteudo.add(tituloPrincipal);
            
            // Seção 1: Dados Pessoais
            adicionarSecao("📋 DADOS PESSOAIS", dadosPessoaisExpandido, () -> {
                JPanel painel = criarPainelGrid();
                adicionarCampo(painel, "ID:", String.valueOf(pessoa.getId()), criarIcone("id"));
                adicionarCampo(painel, "Nº do BI:", pessoa.getNumeroBI(), criarIcone("bi"));
                adicionarCampo(painel, "Nome Completo:", pessoa.getNomeCompleto(), criarIcone("nome"));
                adicionarCampo(painel, "Data de Nascimento:", pessoa.getDataNascimento(), criarIcone("data"));
                adicionarCampo(painel, "Gênero:", pessoa.getGenero(), criarIcone("genero"));
                adicionarCampo(painel, "Naturalidade:", pessoa.getNaturalidade(), criarIcone("naturalidade"));
                adicionarCampo(painel, "Estado Civil:", pessoa.getEstadoCivil(), criarIcone("estado"));
                adicionarCampo(painel, "Altura:", pessoa.getAltura(), criarIcone("altura"));
                return painel;
            });
            
            // Seção 2: Filiação
            adicionarSecao("👨‍👩‍👧 FILIAÇÃO", filiacaoExpandido, () -> {
                JPanel painel = criarPainelGrid();
                adicionarCampo(painel, "Nome do Pai:", pessoa.getNomePai(), criarIcone("pai"));
                adicionarCampo(painel, "Nome da Mãe:", pessoa.getNomeMae(), criarIcone("mae"));
                return painel;
            });
            
            // Seção 3: Documentação
            adicionarSecao("📄 DOCUMENTAÇÃO", documentosExpandido, () -> {
                JPanel painel = criarPainelGrid();
                adicionarCampo(painel, "Documento Emitido Em:", pessoa.getDocumentoEmitidoEm(), criarIcone("data"));
                adicionarCampo(painel, "Documento Válido Até:", pessoa.getDocumentoValidoAte(), criarIcone("data"));
                return painel;
            });
            
            // Seção 4: Endereço
            adicionarSecao("🏠 ENDEREÇO", enderecoExpandido, () -> {
                JPanel painel = criarPainelGrid();
                adicionarCampo(painel, "Endereço Residencial:", pessoa.getResidencia(), criarIcone("endereco"));
                adicionarCampo(painel, "ID do Usuário:", String.valueOf(pessoa.getUsuarioId()), criarIcone("usuario"));
                return painel;
            });
            
            // Adicionar espaço extra no final
            painelConteudo.add(Box.createVerticalStrut(20));
            
        } catch (NumberFormatException e) {
            exibirMensagemErro("ID do usuário inválido!");
        } catch (Exception e) {
            exibirMensagemErro("Erro ao carregar dados: " + e.getMessage());
        }
        
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }
    
    private void adicionarSecao(String titulo, boolean expandido, SecaoConteudoProvider provider) {
        // Painel da seção
        JPanel secaoPainel = new JPanel();
        secaoPainel.setLayout(new BoxLayout(secaoPainel, BoxLayout.Y_AXIS));
        secaoPainel.setBackground(Color.WHITE);
        secaoPainel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Botão do cabeçalho
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tituloLabel.setForeground(new Color(52, 73, 94));
        
        JLabel iconeLabel = new JLabel(expandido ? "▼" : "▶");
        iconeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        iconeLabel.setForeground(new Color(52, 73, 94));
        
        headerPanel.add(tituloLabel, BorderLayout.WEST);
        headerPanel.add(iconeLabel, BorderLayout.EAST);
        
        // Adicionar mouse listener para expandir/recolher
        headerPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                toggleSecao(titulo, secaoPainel, iconeLabel, provider);
            }
            
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                headerPanel.setBackground(new Color(248, 248, 248));
                tituloLabel.setForeground(new Color(41, 128, 185));
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                headerPanel.setBackground(Color.WHITE);
                tituloLabel.setForeground(new Color(52, 73, 94));
            }
        });
        
        secaoPainel.add(headerPanel);
        
        // Conteúdo da seção
        if (expandido) {
            JPanel conteudoPanel = provider.getConteudo();
            conteudoPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
            secaoPainel.add(conteudoPanel);
        }
        
        painelConteudo.add(secaoPainel);
        painelConteudo.add(Box.createVerticalStrut(5));
    }
    
    private void toggleSecao(String titulo, JPanel secaoPainel, JLabel iconeLabel, SecaoConteudoProvider provider) {
        // Determinar qual seção está sendo clicada
        if (titulo.contains("DADOS PESSOAIS")) {
            dadosPessoaisExpandido = !dadosPessoaisExpandido;
        } else if (titulo.contains("FILIAÇÃO")) {
            filiacaoExpandido = !filiacaoExpandido;
        } else if (titulo.contains("DOCUMENTAÇÃO")) {
            documentosExpandido = !documentosExpandido;
        } else if (titulo.contains("ENDEREÇO")) {
            enderecoExpandido = !enderecoExpandido;
        }
        
        // Remover o conteúdo atual
        if (secaoPainel.getComponentCount() > 1) {
            secaoPainel.remove(1);
        }
        
        // Atualizar ícone
        boolean expandido = iconeLabel.getText().equals("▼");
        iconeLabel.setText(expandido ? "▶" : "▼");
        
        // Adicionar novo conteúdo se expandido
        if (!expandido) {
            JPanel conteudoPanel = provider.getConteudo();
            conteudoPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));
            secaoPainel.add(conteudoPanel);
        }
        
        secaoPainel.revalidate();
        secaoPainel.repaint();
        
        // Ajustar tamanho da janela
        pack();
        setLocationRelativeTo(null);
    }
    
    private JPanel criarPainelGrid() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(Color.WHITE);
        return painel;
    }
    
    private void adicionarCampo(JPanel painel, String label, String valor, ImageIcon icone) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Label
        JLabel lblLabel = new JLabel(label, icone, JLabel.LEADING);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 13));
        lblLabel.setForeground(new Color(85, 85, 85));
        lblLabel.setIconTextGap(8);
        gbc.gridx = 0;
        gbc.gridy = painel.getComponentCount() / 2;
        gbc.weightx = 0.35;
        painel.add(lblLabel, gbc);
        
        // Valor
        JLabel lblValor = new JLabel(valor != null && !valor.isEmpty() ? valor : "—");
        lblValor.setFont(new Font("Arial", Font.PLAIN, 13));
        lblValor.setForeground(new Color(51, 51, 51));
        gbc.gridx = 1;
        gbc.weightx = 0.65;
        painel.add(lblValor, gbc);
    }
    
    private ImageIcon criarIcone(String tipo) {
        int size = 18;
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        switch(tipo) {
            case "id":
                g2d.setColor(new Color(52, 152, 219));
                g2d.fillOval(2, 2, size-4, size-4);
                break;
            case "bi":
                g2d.setColor(new Color(46, 204, 113));
                g2d.fillRect(4, 4, size-8, size-8);
                break;
            case "nome":
                g2d.setColor(new Color(155, 89, 182));
                g2d.fillOval(4, 4, size-8, size-8);
                break;
            case "data":
                g2d.setColor(new Color(230, 126, 34));
                g2d.fillRect(4, 4, size-8, size-8);
                break;
            case "genero":
                g2d.setColor(new Color(241, 76, 139));
                g2d.drawOval(4, 4, size-8, size-8);
                break;
            case "naturalidade":
                g2d.setColor(new Color(39, 174, 96));
                int[] x = {size/2, 4, size-4};
                int[] y = {4, size-4, size-4};
                g2d.fillPolygon(x, y, 3);
                break;
            case "estado":
                g2d.setColor(new Color(142, 68, 173));
                g2d.fillRoundRect(4, 4, size-8, size-8, 3, 3);
                break;
            case "altura":
                g2d.setColor(new Color(241, 76, 139));
                g2d.drawLine(size/2, 4, size/2, size-4);
                g2d.drawLine(size/2-3, size/2, size/2+3, size/2);
                break;
            case "pai":
                g2d.setColor(new Color(52, 73, 94));
                g2d.fillRect(4, 6, size-8, size-12);
                break;
            case "mae":
                g2d.setColor(new Color(241, 76, 139));
                g2d.fillOval(6, 4, 6, 6);
                break;
            case "endereco":
                g2d.setColor(new Color(230, 126, 34));
                g2d.fillRect(6, 4, 6, size-8);
                break;
            default:
                g2d.setColor(Color.GRAY);
                g2d.fillOval(4, 4, size-8, size-8);
        }
        
        g2d.dispose();
        return new ImageIcon(image);
    }
    
    private void exibirMensagemErro(String mensagem) {
        JLabel erroLabel = new JLabel(mensagem);
        erroLabel.setFont(new Font("Arial", Font.BOLD, 14));
        erroLabel.setForeground(Color.RED);
        erroLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelConteudo.add(erroLabel);
        painelConteudo.revalidate();
        painelConteudo.repaint();
    }
    
    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(120, 35));
        
        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(cor);
            }
        });
        
        return botao;
    }
    
    private void editarPessoa() {
        String idUsuario = idUsuarioProvider.get();
        if (idUsuario != null && !idUsuario.isEmpty()) {
            JFrame frame = new JFrame();
            frame.add(new FormCadastroPessoa(() -> idUsuario));
            frame.setSize(400, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            dispose();
        }
    }
    
    @FunctionalInterface
    interface SecaoConteudoProvider {
        JPanel getConteudo();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TabelaPessoa(() -> "1").setVisible(true);
        });
    }
}