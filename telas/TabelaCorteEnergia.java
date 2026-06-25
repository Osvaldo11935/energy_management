package telas;

import models.CorteEnergiaModelo;
import modeloFiles.CorteEnergiaFile;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TabelaCorteEnergia extends JFrame {

    private final CorteEnergiaFile corteEnergiaFile = CorteEnergiaFile.instaciar();

    private List<CorteEnergiaModelo> corteEnergias;

    private JPanel painelTabela;

    public TabelaCorteEnergia() {
        setTitle("Corte Energias");
        setSize(700, 400);
        setLocationRelativeTo(null);
        
        carregarTabela();
    }

    private void carregarTabela() {

        corteEnergias = corteEnergiaFile.listar();

        String[] colunas = {
                "ID",
                "Nome do cliente",
                "Motivo",
                "Estado",
                "Data do corte"
        };

        List<Object[]> dados =
                corteEnergias.stream()
                        .map(u -> new Object[]{
                                u.getId(),
                                u.getCliente().getUsuario().getNomeUsuario(),
                                u.getMotivo(),
                                u.getStatus(),
                                u.getDataInicio()
                        })
                        .toList();

        JPanel novaTabela =
                TabelaGerador.criarTabelaComAcoes(
                        colunas,
                        dados,
                        criarAcoes(),
                        null,
                        null
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

        return List.of(

                new TabelaGerador.AcaoTabela() {

                    @Override
                    public String getNome() {
                        return "Editar";
                    }

                    @Override
                    public void executar(
                            int linha,
                            Object[] dadosLinha
                    ) {

                        //CorteEnergiaModelo corteEnergiaItem = buscarCorteEnergia(dadosLinha);
                    }
                }
            );
    }
    /*
    private CorteEnergiaModelo buscarCorteEnergia(Object[] dadosLinha) {

        int id = getId(dadosLinha);

        return corteEnergias.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private int getId(Object[] dadosLinha) {

        return Integer.parseInt(
                dadosLinha[0].toString()
        );
    }
    */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                () ->new TabelaCorteEnergia().setVisible(true)
        );
    }
}