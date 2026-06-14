package telas;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.SwingUtilities;

import modeloFiles.PerfilFile;
import models.PerfilModelo;

public class TabelaPerfil  extends JFrame{
    public TabelaPerfil()
    {
        setTitle("Perfis");
        setSize(700, 400);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);

        String[] colunas = {"ID", "Nome", "Descrição"};

        PerfilFile arquivo = new PerfilFile(new PerfilModelo());
        List<Object[]> dados = new ArrayList<>();

        for(PerfilModelo p : arquivo.listar())
        {
             dados.add(new Object[]{
                p.getId(),
                p.getNome(),
                p.getDescricao()
             });
        }

        List<TabelaGerador.AcaoTabela> acoes = new ArrayList<>();

        acoes.add(new TabelaGerador.AcaoTabela() {
            @Override
            public  void executar(int linha, Object[] dadosLinha)
            {
                JOptionPane.showMessageDialog(null, "Editar: " + dadosLinha[1]);
            }
            @Override
            public String getNome(){
                 return "Editar";
            }
        });

        acoes.add(new TabelaGerador.AcaoTabela() {
            @Override
            public void executar(int linha, Object[] dadosLinha)
            {
                int confirm = JOptionPane.showConfirmDialog(null, "Remover: " + dadosLinha[1] + "?");

                if(confirm == JOptionPane.YES_OPTION)
                {
                    JOptionPane.showMessageDialog(null, "Removido!");
                }
            }
            
            @Override
            public String getNome()
            {
                return "Remover";
            }
        });
        JPanel tabela = TabelaGerador.criarTabelaComAcoes(colunas, dados, acoes,
             "Novo",() -> {SwingUtilities.invokeLater(()-> new FormCadastroPerfil().setVisible(true));});
        add(tabela, BorderLayout.CENTER);
    }
    public static void main(String args[])
    {
        SwingUtilities.invokeLater(()-> {
             new TabelaPerfil().setVisible(true);
        });
    }
}
