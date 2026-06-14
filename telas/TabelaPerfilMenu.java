package telas;

import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*;

import modeloFiles.PerfilMenuFile;
import models.PerfilMenuModelo;

public class TabelaPerfilMenu  extends  JFrame {

    private final PerfilMenuFile perfilMenuFile = new PerfilMenuFile(new PerfilMenuModelo());

    private final List<PerfilMenuModelo> perfilMenu;

    public TabelaPerfilMenu() {

        setTitle("Perfil Menus");
        setSize(700, 400);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        perfilMenu = perfilMenuFile.listar(); 

        String[] colunas = {"ID", "Perfil", "Menu", "Pode Visualizar", "Pode Criar", "Pode Editar", "Pode Eliminar"};

        List<Object[]> dados = perfilMenu.stream()
                .map(u -> new Object[]{
                        u.getId(),
                        u.getPerfil().getNome(),
                        u.getMenu().getNome(),
                        u.isPodeVisualizar(),
                        u.isPodeCriar(),
                        u.isPodeCriar(),
                        u.isPodeEliminar()
                })
                .toList();

        List<TabelaGerador.AcaoTabela> acoes = criarAcoes();

        JPanel tabela = TabelaGerador.criarTabelaComAcoes(
                colunas,
                dados,
                acoes,
                "Novo",
                this::abrirNovoPerfilMenu
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
                        PerfilMenuModelo PerfilMenu = buscarPerfilMenu(dadosLinha);
                        SwingUtilities.invokeLater(() ->
                                 new FormWindow("Atualizar PerfilMenu", 456, 628, new FormCadastroPerfilMenu(PerfilMenu)).setVisible(true)
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

    private PerfilMenuModelo buscarPerfilMenu(Object[] dadosLinha) {
        int id = getId(dadosLinha);
        return perfilMenu.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private int getId(Object[] dadosLinha) {
        return Integer.parseInt(dadosLinha[0].toString());
    }

    private void abrirNovoPerfilMenu() {
        SwingUtilities.invokeLater(() ->
                new FormWindow("Novo PerfilMenu", 466, 270, new FormCadastroPerfilMenu()).setVisible(true)
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TabelaPerfilMenu().setVisible(true)
        );
    }
}