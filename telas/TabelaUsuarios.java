package telas;

import models.PessoaModelo;
import models.UsuarioModelo;
import modeloFiles.PessoaFile;
import modeloFiles.UsuarioFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class TabelaUsuarios extends JFrame {

    private final UsuarioFile usuarioFile =
            new UsuarioFile(new UsuarioModelo());

    private final PessoaFile pessoaFile =
            new PessoaFile(new PessoaModelo());

    private List<UsuarioModelo> usuarios;

    private JPanel painelTabela;

    public TabelaUsuarios() {

        setTitle("Usuários");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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
                        return "Adicionar Dados Pessoais";
                    }

                    @Override
                    public void executar(
                            int linha,
                            Object[] dadosLinha
                    ) {

                        int id =
                                getId(dadosLinha);

                        PessoaModelo pessoa =
                                pessoaFile
                                        .buscarPorUsuarioId(id);

                        if (pessoa != null) {

                            JOptionPane.showMessageDialog(
                                    TabelaUsuarios.this,
                                    "Usuário já tem dados pessoais cadastrados"
                            );

                            return;
                        }

                        FormWindow tela =
                                new FormWindow(
                                        "Dados Pessoais",
                                        456,
                                        510,
                                        new FormPessoa(
                                                () -> String.valueOf(id)
                                        )
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
                        return "Detalhe";
                    }

                    @Override
                    public void executar(
                            int linha,
                            Object[] dadosLinha
                    ) {

                        int id =
                                getId(dadosLinha);

                        PessoaModelo pessoa =
                                pessoaFile
                                        .buscarPorUsuarioId(id);

                        if (pessoa == null) {

                            JOptionPane.showMessageDialog(
                                    TabelaUsuarios.this,
                                    "Usuário sem dados pessoais. Cadastre primeiro."
                            );

                            return;
                        }

                        SwingUtilities.invokeLater(() ->new DetalhePessoa(() -> String.valueOf(id)).setVisible(true));
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