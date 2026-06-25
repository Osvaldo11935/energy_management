package telas;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import modeloFiles.SolicitacaoFile;
import models.SolicitacaoModelo;
import models.UsuarioModelo;
import utils.Session;

public class TabelaSolicitacao extends JFrame {
    private UsuarioModelo usuarioLogado;
    private final SolicitacaoFile arquivo = SolicitacaoFile.instaciar();

    private List<SolicitacaoModelo> solicitacoes;

    private JPanel painelTabela;

    public TabelaSolicitacao() {
        usuarioLogado = Session.getUsuario();
        setTitle("Solicitações");
        setSize(700, 400);
        setLocationRelativeTo(null);
        
        carregarTabela();
    }

    private void carregarTabela() {
         
        solicitacoes = usuarioLogado.ehTecnico() ? arquivo.buscarPorTecnicoResponsavelId(usuarioLogado.getId())
                                                 : arquivo.buscarPorUsuarioId(usuarioLogado.getId());

        String[] colunas = {
                "ID",
                "Tipo Contrato",
                "Estado",
                "Tipo de Solicitação",
                "Prioridade"
        };

        List<Object[]> dados =
                new ArrayList<>();

        for (SolicitacaoModelo p : solicitacoes) {

            dados.add(
                    new Object[]{
                            p.getId(),
                            p.getContrato().getTipoContrato(),
                            p.getStatus(),
                            p.getTipoSolicitacao(),
                            p.getPrioridade()
                    }
            );
        }

        JPanel novaTabela =
                TabelaGerador.criarTabelaComAcoes(
                        colunas,
                        dados,
                        criarAcoes(),
                        "Novo",
                        this::abrirNovoSolicitacao
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

        List<TabelaGerador.AcaoTabela> acoes =
                new ArrayList<>();

        acoes.add(new TabelaGerador.AcaoTabela() {
                    @Override
                    public void executar(int linha, Object[] dadosLinha) {

                        SolicitacaoModelo Solicitacao = buscarSolicitacao(dadosLinha);
                        FormWindow tela =
                            new FormWindow(
                                    "Atualizar Solicitacao",
                                    456,
                                    396,
                                    new FormSolicitacao(Solicitacao)
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
                                        TabelaSolicitacao.this,
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

                            JOptionPane.showMessageDialog(TabelaSolicitacao.this, "Removido!");
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

                        SolicitacaoModelo Solicitacao = buscarSolicitacao(dadosLinha);
                        FormWindow tela =
                            new FormWindow(
                                    "Interação na Solicitacao",
                                    600,
                                    400,
                                    new SolicitacaoDetalhe(Solicitacao)
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
                        return "Interagir";
                    }
                }
        );
        return acoes;
    }

    private SolicitacaoModelo buscarSolicitacao(
            Object[] dadosLinha
    ) {

        int id = getId(dadosLinha);
        return solicitacoes
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

    private void abrirNovoSolicitacao() {
       
        FormWindow tela =new FormWindow(
            "Nova Solicitacao",
            456,
            396,
            new FormSolicitacao()
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
        SwingUtilities.invokeLater(() ->new TabelaSolicitacao().setVisible(true));
    }
}