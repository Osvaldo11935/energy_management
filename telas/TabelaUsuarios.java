package telas;

import models.PessoaModelo;
import models.UsuarioModelo;
import modeloFiles.PessoaFile;
import modeloFiles.UsuarioFile;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TabelaUsuarios extends JFrame {

    private final UsuarioFile usuarioFile = new UsuarioFile(new UsuarioModelo());
    private final PessoaFile pessoaFile = new PessoaFile(new PessoaModelo());

    private final List<UsuarioModelo> usuarios;

    public TabelaUsuarios() {

        setTitle("Usuários");
        setSize(700, 400);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        usuarios = usuarioFile.listar(); 

        String[] colunas = {"ID", "Nome", "Email", "Telefone"};

        List<Object[]> dados = usuarios.stream()
                .map(u -> new Object[]{
                        u.getId(),
                        u.getNomeUsuario(),
                        u.getEmail(),
                        u.getNumeroTelefone()
                })
                .toList();

        List<TabelaGerador.AcaoTabela> acoes = criarAcoes();

        JPanel tabela = TabelaGerador.criarTabelaComAcoes(
                colunas,
                dados,
                acoes,
                "Novo",
                this::abrirNovoUsuario
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
                        UsuarioModelo usuario = buscarUsuario(dadosLinha);
                        SwingUtilities.invokeLater(() ->
                                 new FormWindow("Atualizar Usuario", 456, 387, new FormCadastroUsuario(usuario)).setVisible(true)
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
                },

                new TabelaGerador.AcaoTabela() {
                    public String getNome() {
                        return "Adicionar Dados Pessoais";
                    }

                    public void executar(int linha, Object[] dadosLinha) {

                        int id = getId(dadosLinha);

                        PessoaModelo pessoa = pessoaFile.buscarPorUsuarioId(id);

                        if (pessoa != null) {
                            JOptionPane.showMessageDialog(null,
                                    "Usuário já tem dados pessoais cadastrados");
                            return;
                        }

                        SwingUtilities.invokeLater(() ->
                                new FormWindow("Dados Pessoais",456, 600, new FormCadastroPessoa(() -> String.valueOf(id))).setVisible(true)
                        );
                    }
                },

                new TabelaGerador.AcaoTabela() {
                    public String getNome() {
                        return "Detalhe";
                    }

                    public void executar(int linha, Object[] dadosLinha) {

                        int id = getId(dadosLinha);

                        PessoaModelo pessoa = pessoaFile.buscarPorUsuarioId(id);

                        if (pessoa == null) {
                            JOptionPane.showMessageDialog(null,
                                    "Usuário sem dados pessoais. Cadastre primeiro.");
                            return;
                        }

                        SwingUtilities.invokeLater(() ->
                                new TabelaPessoa(() -> String.valueOf(id)).setVisible(true)
                        );
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

    private int getId(Object[] dadosLinha) {
        return Integer.parseInt(dadosLinha[0].toString());
    }

    private void abrirNovoUsuario() {
        SwingUtilities.invokeLater(() ->
                new FormWizard().setVisible(true)
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new TabelaUsuarios().setVisible(true)
        );
    }
}