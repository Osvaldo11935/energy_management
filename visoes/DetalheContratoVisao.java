package visoes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.*;

import enums.TipoContratoEnum;
import modeloFiles.AtalhoFile;
import modeloFiles.ClienteFile;
import modeloFiles.ContadorFile;
import modelos.AtalhoModelo;
import modelos.ClienteModelo;
import modelos.ContadorModelo;
import modelos.LeituraConsumoModelo;
import modelos.UsuarioModelo;
import utils.Session;
import visoes.componentes.*;

public class DetalheContratoVisao {

    private JanelaDetalheBuilder builder;
    private ClienteFile clienteFile;
    private ContadorFile contadorFile;

    private JPanel ultimoPainel;
    private Supplier<String> idUsuarioProvider;

    public DetalheContratoVisao() {
        clienteFile = ClienteFile.instaciar();
        contadorFile = ContadorFile.instaciar();

        builder = new JanelaDetalheBuilder("Detalhes do Contrato");
    }

    public JPanel criarJanela(Supplier<String> idUsuarioProvider) {
        this.idUsuarioProvider = idUsuarioProvider;
        return montarTela();
    }

    private JPanel montarTela() {
        String idUsuario = idUsuarioProvider.get();
        JPanel painelErro = new JPanel();

        if (idUsuario == null || idUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID do usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return painelErro;
        }

        try {
            List<ClienteModelo> clientes =
                    clienteFile.buscarClientePorUsuarioId(Integer.parseInt(idUsuario));

            if (clientes == null || clientes.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhum contrato encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return painelErro;
            }

            JPanel novoPainel;

            if (clientes.size() > 1)
                novoPainel = criarJanelaComAbas(clientes, idUsuario);
            else
                novoPainel = criarPainelContrato(clientes.get(0), idUsuario);

            ultimoPainel = novoPainel;
            return novoPainel;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID do usuário inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return painelErro;
    }

    private void recarregarTela() {
        if (ultimoPainel == null) return;

        Container parent = ultimoPainel.getParent();
        if (parent == null) return;

        parent.removeAll();
        parent.add(montarTela());
        parent.revalidate();
        parent.repaint();
    }

    private JPanel criarJanelaComAbas(List<ClienteModelo> clientes, String idUsuario) {

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        for (ClienteModelo cliente : clientes) {
            JPanel contratoPanel = criarPainelContrato(cliente, idUsuario);
            tabbedPane.addTab("Contrato #" + cliente.getId(), contratoPanel);
        }

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel criarPainelContrato(ClienteModelo cliente, String idUsuario) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        ContadorModelo contador = contadorFile.buscarPorClienteId(cliente.getId());

        builder = new JanelaDetalheBuilder("")
                .setTamanho(600, 500)
                .adicionarTitulo("INFORMAÇÕES DO CONTRATO")

                .adicionarSecao("dadosContrato", "DADOS DO CONTRATO", true, contentPanel -> {

                    PainelCamposGrid grid = new PainelCamposGrid();

                    grid.adicionarCampo("ID do Contrato:", String.valueOf(cliente.getId()));
                    grid.adicionarCampo("Tipo de Contrato:", cliente.getTipoContrato());
                    grid.adicionarCampo("Tipo de Cliente:", cliente.getTipoCliente());
                    grid.adicionarCampo("Saldo Devedor:", formatarMoeda(cliente.getSaldoDevedor()));
                    grid.adicionarCampo("Crédito Disponível:", formatarMoeda(cliente.getCreditoDisponivel()));
                    grid.adicionarCampo("Limite de Consumo Mensal:", formatarMoeda(cliente.getLimiteConsumoMensal()));
                    grid.adicionarCampo("Data do Contrato:", cliente.getDataCadastro());
                    grid.adicionarCampo("Área de Distribuição:", cliente.getAreaDistribuicao().getProvincia() + " - " + cliente.getAreaDistribuicao().getBairro());
                    grid.adicionarCampo("Observações:", cliente.getObservacoes());
                    
                    contentPanel.add(grid, BorderLayout.CENTER);
                    builder.adicionarBotoesSecao(contentPanel, botoesContrato(contador, cliente));
                    
                });

        if (contador != null) {

            builder.adicionarSecao("dadosContador", "DADOS DO CONTADOR", false, contentPanel -> {

                PainelCamposGrid grid = new PainelCamposGrid();

                grid.adicionarCampo("ID do contador", String.valueOf(contador.getId()));
                grid.adicionarCampo("Numero de Serie", contador.getNumeroSerie());
                grid.adicionarCampo("Tipo de Contador", contador.getTipoContador());
                grid.adicionarCampo("Limite de Consumo", formatarMoeda(contador.getLimiteConsumo()));
                grid.adicionarCampo("Data da Instalação", contador.getDataInstalacao());
                
                if(!Session.getUsuario().ehCliente()){
                    contentPanel.add(grid, BorderLayout.CENTER);

                    builder.adicionarBotoesSecao(contentPanel,
                            new BotaoSecao("Editar", e -> {
                                abrirTela(
                                    "Atualizar Contador",
                                    456,
                                    387,
                                    new FormContadorVisao(contador)
                                );
                            }),

                            new BotaoSecao("Excluir", e -> {
                                int confirmar = JOptionPane.showConfirmDialog(null, "Deseja excluir?");
                                if (confirmar == JOptionPane.YES_OPTION) {
                                    contadorFile.remover(contador.getId());
                                    recarregarTela();
                                }
                            })
                    );
                }
                else
                {
                    contentPanel.add(grid, BorderLayout.CENTER);
                    builder.adicionarBotoesSecao(contentPanel,
                            new BotaoSecao("Gerar Consumo", e -> {
                                List<AtalhoModelo> atalhos = new AtalhoFile(new AtalhoModelo()).listar();
            
                                int novoId = (atalhos == null || atalhos.isEmpty())? 1: atalhos.getLast().getId() + 1;
                                
                                LeituraConsumoModelo leituraConsumo = new LeituraConsumoModelo(
                                   novoId,
                                   contador.getId(),
                                   1000,
                                   5000,
                                   LocalDate.now().toString(),
                                   LocalDate.now().toString(),
                                   0 
                                );

                                leituraConsumo.salvarDados();
                            })
                    );
                }
            });
        }

        panel.add(builder.construirPainel(), BorderLayout.CENTER);
        return panel;
    }

    private String formatarMoeda(Object valor) {
        if (valor == null) return "0.00";

        try {
            double valorNumerico;

            if (valor instanceof Double) {
                valorNumerico = (Double) valor;
            } else if (valor instanceof Number) {
                valorNumerico = ((Number) valor).doubleValue();
            } else {
                valorNumerico = Double.parseDouble(valor.toString());
            }

            return String.format("%.2f", valorNumerico);

        } catch (Exception e) {
            return valor.toString();
        }
    }
    private BotaoSecao[] botoesContrato(ContadorModelo contador, ClienteModelo cliente) {
        List<BotaoSecao> botoes = new ArrayList<>();

        UsuarioModelo usuarioLogado = Session.getUsuario();

        if(usuarioLogado.ehCliente())
        {
            if(cliente.getTipoContrato().toLowerCase().equals(TipoContratoEnum.POS_PAGO.toString())){
                botoes.add(new BotaoSecao("Faturas", e -> {new TabelaFaturaVisao(cliente.getId()).setVisible(true);}));
            }
            else{
                botoes.add(new BotaoSecao("Saldo", e -> {new TabelaFaturaVisao(cliente.getId()).setVisible(true);}));
            }
        }
        else
        {
            if (contador == null) {
                botoes.add(
                    new BotaoSecao("Novo Contador", e -> {
                        abrirTela(
                            "Novo Contador",
                            456,
                            300,
                            new FormContadorVisao(() -> String.valueOf(cliente.getId()))
                        );
                    })
                );
            }

            botoes.add(
                new BotaoSecao("Editar", e -> {
                    abrirTela(
                        "Atualizar Contrato",
                        456,
                        387,
                        new FormClienteVisao(cliente)
                    );
                })
            );

            botoes.add(
                new BotaoSecao("Excluir", e -> {
                    int confirmar =
                        JOptionPane.showConfirmDialog(
                            null,
                            "Deseja excluir?"
                        );

                    if (confirmar == JOptionPane.YES_OPTION) {
                        clienteFile.remover(cliente.getId());
                        recarregarTela();
                    }
                })
            );

        }

        return botoes.toArray(new BotaoSecao[0]);
    }
    
    private void abrirTela(String titulo, int largura, int altura, JPanel form) {
        FormWindow tela = new FormWindow(
                titulo,
                largura,
                altura,
                form
        );

        tela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                recarregarTela();
            }
        });

        tela.setVisible(true);
    }
}
