package telas;

import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;
import modeloFiles.AreaDistribuicaoFile;
import models.AreaDistribuicaoModelo;

public class TabelaAreaDistribuicao  extends  JFrame {

    private final AreaDistribuicaoFile areaDistribuicaoFile = new AreaDistribuicaoFile(new AreaDistribuicaoModelo());

    private final List<AreaDistribuicaoModelo> areaDistribuicao;

    public TabelaAreaDistribuicao() {

        setTitle("Area de Distribuições");
        setSize(700, 400);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        areaDistribuicao = areaDistribuicaoFile.listar(); 

        String[] colunas = {"ID", "Provincia", "Municipio", "Comuna", "Bairro", "Subestação"};

        List<Object[]> dados = areaDistribuicao.stream()
                .map(u -> new Object[]{
                        u.getId(),
                        u.getProvincia(),
                        u.getMunicipio(),
                        u.getComuna(),
                        u.getBairro(),
                        u.getSubestacaoId()
                })
                .toList();

        List<TabelaGerador.AcaoTabela> acoes = criarAcoes();

        JPanel tabela = TabelaGerador.criarTabelaComAcoes(
                colunas,
                dados,
                acoes,
                "Novo",
                this::abrirNovoAreaDistribuicao
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
                        AreaDistribuicaoModelo areaDistribuicao = buscarAreaDistribuicao(dadosLinha);
                        SwingUtilities.invokeLater(() ->
                                 new FormWindow("Atualizar Area de Distribuição", 456, 387, new FormAreaDistribuicao(areaDistribuicao)).setVisible(true)
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

    private AreaDistribuicaoModelo buscarAreaDistribuicao(Object[] dadosLinha) {
        int id = getId(dadosLinha);
        return areaDistribuicao.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private int getId(Object[] dadosLinha) {
        return Integer.parseInt(dadosLinha[0].toString());
    }

    private void abrirNovoAreaDistribuicao() {
        SwingUtilities.invokeLater(() ->
                new FormWindow("Novo AreaDistribuicao", 456, 387, new FormAreaDistribuicao()).setVisible(true)
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TabelaAreaDistribuicao().setVisible(true)
        );
    }
}