package telas;

import models.UsuarioModelo;
import modeloFiles.UsuarioFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class TabelaUsuarios extends JFrame {

    private final UsuarioFile usuarioFile =
            new UsuarioFile(new UsuarioModelo());

    private List<UsuarioModelo> usuarios;

    private JPanel painelTabela;

    public TabelaUsuarios() {

        setTitle("Usuários");
        setSize(700, 400);
        setLocationRelativeTo(null);
        
        carregarTabela();
    }

    private void carregarTabela() {

        usuarios = usuarioFile.listar();

        String[] colunas = {
                "ID",
                "Nome",
                "Email",
                "Telefone",
                "Perfil"
        };

        List<Object[]> dados =
                usuarios.stream()
                        .map(u -> new Object[]{
                                u.getId(),
                                u.getNomeUsuario(),
                                u.getEmail(),
                                u.getNumeroTelefone(),
                                u.getPerfil().getNome()
                        })
                        .toList();

        JPanel novaTabela =
                TabelaGerador.criarTabelaComAcoes(
                        colunas,
                        dados,
                        criarAcoes(),
                        "Novo",
                        this::abrirNovoUsuario
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

                        UsuarioModelo usuario =
                                buscarUsuario(dadosLinha);

                        FormWindow tela =
                                new FormWindow(
                                        "Atualizar Usuário",
                                        456,
                                        387,
                                        new FormUsuario(usuario)
                                );

                        tela.addWindowListener(
                                new WindowAdapter() {

                                    @Override
                                    public void windowClosed(
                                            WindowEvent e
                                    ) {
                                        carregarTabela();
                                    }
                                }
                        );

                        tela.setVisible(true);
                    }
                },

                new TabelaGerador.AcaoTabela() {

                    @Override
                    public String getNome() {
                        return "Remover";
                    }

                    @Override
                    public void executar(
                            int linha,
                            Object[] dadosLinha
                    ) {

                        int confirm =
                                JOptionPane.showConfirmDialog(
                                        TabelaUsuarios.this,
                                        "Remover "
                                                + dadosLinha[1]
                                                + "?",
                                        "Confirmação",
                                        JOptionPane.YES_NO_OPTION
                                );

                        if (confirm
                                == JOptionPane.YES_OPTION) {

                            int id =
                                    getId(dadosLinha);

                            usuarioFile.remover(id);

                            carregarTabela();

                            JOptionPane.showMessageDialog(
                                    TabelaUsuarios.this,
                                    "Removido!"
                            );
                        }
                    }
                },
                new TabelaGerador.AcaoTabela() {
                    @Override
                    public String getNome() {
                        return "Detalhe";
                    }

                    @Override
                    public void executar(int linha, Object[] dadosLinha) {

                        int id = getId(dadosLinha);
                        SwingUtilities.invokeLater(() ->new DetalheUsuario(() -> String.valueOf(id)).setVisible(true));
                    }
                }
        );
    }

    private UsuarioModelo buscarUsuario(Object[] dadosLinha) {

        int id = getId(dadosLinha);

        return usuarios.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private int getId(
            Object[] dadosLinha
    ) {

        return Integer.parseInt(
                dadosLinha[0].toString()
        );
    }

    private void abrirNovoUsuario() {
        FormWizard tela = new FormWizard();
        tela.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosed( WindowEvent e) {
                        carregarTabela();
                    }
                }
        );

        tela.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(
                () ->
                        new TabelaUsuarios()
                                .setVisible(true)
        );
    }
}