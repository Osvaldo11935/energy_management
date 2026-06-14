package telas;

import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;
import modeloFiles.SubestacaoFile;
import models.SubestacaoModelo;

public class TabelaSubestacao  extends  JFrame {

    private final SubestacaoFile SubestacaoFile = new SubestacaoFile(new SubestacaoModelo());

    private final List<SubestacaoModelo> Subestacao;

    public TabelaSubestacao() {

        setTitle("Subestacaos");
        setSize(700, 400);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Subestacao = SubestacaoFile.listar(); 

        String[] colunas = {"ID", "Codigo", "Nome", "Capacidade", "Tensão Nominal", "Responsavel"};

        List<Object[]> dados = Subestacao.stream()
                .map(u -> new Object[]{
                        u.getId(),
                        u.getCodigo(),
                        u.getNome(),
                        u.getCapacidade(),
                        u.getTensaoNominal(),
                        u.getUsuarioId()
                })
                .toList();

        List<TabelaGerador.AcaoTabela> acoes = criarAcoes();

        JPanel tabela = TabelaGerador.criarTabelaComAcoes(
                colunas,
                dados,
                acoes,
                "Novo",
                this::abrirNovoSubestacao
        );

        add(tabela, BorderLayout.CENTER);
    }

    private List<TabelaGerador.AcaoTabela> criarAcoes() {

        return List.of(

                new TabelaGerador.AcaoTabela() {
                    public String getNome() {
                        return "Editar";
                    }

                    public void executar(int linha, Object[] dadosLinha) {
                        SubestacaoModelo Subestacao = buscarSubestacao(dadosLinha);
                        SwingUtilities.invokeLater(() ->
                                 new FormWindow("Atualizar Subestacao", 456, 628, new FormSubestacao(Subestacao)).setVisible(true)
                        );
                    }
                },

                new TabelaGerador.AcaoTabela() {
                    public String getNome() {
                        return "Remover";
                    }

                    public void executar(int linha, Object[] dadosLinha) {

                        int confirm = JOptionPane.showConfirmDialog(
                                null,
                                "Remover " + dadosLinha[1] + "?"
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            JOptionPane.showMessageDialog(null, "Removido!");
                        }
                    }
                }
            );
    }

    private SubestacaoModelo buscarSubestacao(Object[] dadosLinha) {
        int id = getId(dadosLinha);
        return Subestacao.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private int getId(Object[] dadosLinha) {
        return Integer.parseInt(dadosLinha[0].toString());
    }

    private void abrirNovoSubestacao() {
        SwingUtilities.invokeLater(() ->
                new FormWindow("Novo Subestacao", 456, 628, new FormSubestacao()).setVisible(true)
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TabelaSubestacao().setVisible(true)
        );
    }
}