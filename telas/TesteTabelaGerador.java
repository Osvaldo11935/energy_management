package telas;

import javax.swing.*;

import java.util.List;

public class TesteTabelaGerador {
    
    public static void main(String[] args) {
        String[] colunas = {
                "ID",
                "Nome",
                "Email"
        };

        List<Object[]> dados = List.of(
                new Object[]{1, "João", "joao@email.com"},
                new Object[]{2, "Maria", "maria@email.com"}
        );

        List<TabelaGerador.AcaoTabela> acoes =
                new java.util.ArrayList<>();

        acoes.add(new TabelaGerador.AcaoTabela() {

            @Override
            public void executar(
                    int linha,
                    Object[] dadosLinha) {

                JOptionPane.showMessageDialog(
                        null,
                        "Editar: " +
                                dadosLinha[1]);
            }

            @Override
            public String getNome() {
                return "Editar";
            }
        });

        acoes.add(new TabelaGerador.AcaoTabela() {

            @Override
            public void executar(
                    int linha,
                    Object[] dadosLinha) {

                JOptionPane.showMessageDialog(
                        null,
                        "Remover: " +
                                dadosLinha[1]);
            }

            @Override
            public String getNome() {
                return "Remover";
            }
        });

        JPanel tabela =
                TabelaGerador.criarTabelaComAcoes(
                        colunas,
                        dados,
                        acoes,
                    "", ()->{});

        JFrame frame = new JFrame();
        frame.add(new JScrollPane(tabela));
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
