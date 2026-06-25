package telas;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import modeloFiles.NotificacaoFile;
import models.NotificacaoModelo;


public class TabelaNotificacao extends JFrame {

    private final NotificacaoFile arquivo = new NotificacaoFile(new NotificacaoModelo());

    private List<NotificacaoModelo> Notificacaos;

    private JPanel painelTabela;

    public TabelaNotificacao() {

        setTitle("Notificacaos");
        setSize(700, 400);
        setLocationRelativeTo(null);
        
        carregarTabela();
    }

    private void carregarTabela() {

        Notificacaos = arquivo.listar();

        String[] colunas = {
                "ID",
                "Nome do cliente",
                "Area",
                "Titulo",
                "Tipo",
                "Data de Envio"
        };
     
        List<Object[]> dados =
                new ArrayList<>();

        for (NotificacaoModelo p : Notificacaos) {

            dados.add(
                    new Object[]{
                            p.getId(),
                            p.getCliente().getUsuario().getNomeUsuario(),
                            p.getAreaDistribuicao() == null ? "Não Informado": p.getAreaDistribuicao().getBairro(),
                            p.getTitulo(),
                            p.getTipo(),
                            p.getDataEnvio()
                    }
            );
        }
        JPanel novaTabela =
                TabelaGerador.criarTabelaComAcoes(
                        colunas,
                        dados,
                        criarAcoes(),
                        "Novo",
                        this::abrirNovoNotificacao
                );

        if (painelTabela != null) {
            remove(painelTabela);
        }

        painelTabela = novaTabela;

        add(painelTabela, BorderLayout.CENTER);

        revalidate();
        repaint();
    }

    private List<TabelaGerador.AcaoTabela> criarAcoes() {

        List<TabelaGerador.AcaoTabela> acoes = new ArrayList<>();

        acoes.add(new TabelaGerador.AcaoTabela() {
                    @Override
                    public void executar(int linha, Object[] dadosLinha) {

                        NotificacaoModelo Notificacao = buscarNotificacao(dadosLinha);
                        FormWindow tela =
                            new FormWindow(
                                    "Atualizar Notificacao",
                                    456,
                                    348,
                                    new FormNotificacao(Notificacao)
                            );

                        tela.addWindowListener(new WindowAdapter() {
                                    @Override
                                    public void windowClosed(WindowEvent e) {
                                        carregarTabela();
                                    }
                                }
                        );

                        tela.setVisible(true);
                    }

                    @Override
                    public String getNome() {
                        return "Editar";
                    }
                }
        );

        acoes.add(new TabelaGerador.AcaoTabela() {
                    @Override
                    public void executar(int linha, Object[] dadosLinha) {

                        int confirm =
                                JOptionPane.showConfirmDialog(
                                        TabelaNotificacao.this,
                                        "Remover "
                                                + dadosLinha[1]
                                                + "?",
                                        "Confirmação",
                                        JOptionPane.YES_NO_OPTION
                                );

                        if (confirm == JOptionPane.YES_OPTION) {

                            int id = getId(dadosLinha);

                            arquivo.remover(id);

                            carregarTabela();

                            JOptionPane.showMessageDialog(TabelaNotificacao.this, "Removido!");
                        }
                    }

                    @Override
                    public String getNome() {
                        return "Remover";
                    }
                }
        );

        acoes.add(new TabelaGerador.AcaoTabela() {
                    @Override
                    public void executar(int linha, Object[] dadosLinha) {
                        SwingUtilities.invokeLater(() -> new TabelaFaixaConsumo(Integer.parseInt(dadosLinha[0].toString())).setVisible(true));
                    }

                    @Override
                    public String getNome() {
                        return "Faixa de Consumo";
                    }
                }
        );
        return acoes;
    }

    private NotificacaoModelo buscarNotificacao(
            Object[] dadosLinha
    ) {

        int id = getId(dadosLinha);
        return Notificacaos
                .stream()
                .filter(p -> p.getId()  == id)
                .findFirst()
                .orElse(null);
    }

    private int getId(Object[] dadosLinha) {
        return Integer.parseInt(
                dadosLinha[0]
                        .toString()
        );
    }

    private void abrirNovoNotificacao() {
       
        FormWindow tela =new FormWindow(
            "Nova Notificacao",
            456,
            478,
            new FormNotificacao()
        );


        tela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                    carregarTabela();
                }
            }
        );

        tela.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->new TabelaNotificacao().setVisible(true));
    }
}