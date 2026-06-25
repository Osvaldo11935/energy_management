package telas;

import models.PessoaModelo;
import telas.componentes.*;
import modeloFiles.PessoaFile;

import javax.swing.*;

import java.util.function.Supplier;

public class DetalhePessoa {

    public JPanel criarJanela(Supplier<String> idUsuarioProvider) {
        String idUsuario = idUsuarioProvider.get();
        JPanel painelErro = new JPanel();
        if (idUsuario == null || idUsuario.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID do usuário não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return painelErro;
        }
        
        try {
            PessoaFile arquivo = new PessoaFile(new PessoaModelo());
            
            PessoaModelo pessoa = arquivo.buscarPorUsuarioId(Integer.parseInt(idUsuario));
            
            if (pessoa == null) {
                JOptionPane.showMessageDialog(null, "Nenhuma informação encontrada!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return painelErro;
            }

            JanelaDetalheBuilder builder = new JanelaDetalheBuilder("Detalhes da Pessoa")
                .setTamanho(550, 500)
                .adicionarTitulo("INFORMAÇÕES PESSOAIS")
                .adicionarSecao("dadosPessoais", "DADOS PESSOAIS", true, panel -> {
                    PainelCamposGrid grid = new PainelCamposGrid();
                    grid.adicionarCampo("ID:", String.valueOf(pessoa.getId()));
                    grid.adicionarCampo("Nº do BI:", pessoa.getNumeroBI());
                    grid.adicionarCampo("Nome Completo:", pessoa.getNomeCompleto());
                    grid.adicionarCampo("Data de Nascimento:", pessoa.getDataNascimento());
                    grid.adicionarCampo("Gênero:", pessoa.getGenero());
                    grid.adicionarCampo("Naturalidade:", pessoa.getNaturalidade());
                    grid.adicionarCampo("Estado Civil:", pessoa.getEstadoCivil());
                    grid.adicionarCampo("Altura:", pessoa.getAltura());
                    panel.add(grid);
                })
                .adicionarSecao("filiacao", "FILIAÇÃO", false, panel -> {
                    PainelCamposGrid grid = new PainelCamposGrid();
                    grid.adicionarCampo("Nome do Pai:", pessoa.getNomePai());
                    grid.adicionarCampo("Nome da Mãe:", pessoa.getNomeMae());
                    panel.add(grid);
                })
                .adicionarSecao("documentacao", "DOCUMENTAÇÃO", false, panel -> {
                    PainelCamposGrid grid = new PainelCamposGrid();
                    grid.adicionarCampo("Documento Emitido Em:", pessoa.getDocumentoEmitidoEm());
                    grid.adicionarCampo("Documento Válido Até:", pessoa.getDocumentoValidoAte());
                    panel.add(grid);
                })
                .adicionarSecao("endereco", "ENDEREÇO", false, panel -> {
                    PainelCamposGrid grid = new PainelCamposGrid();
                    grid.adicionarCampo("Endereço Residencial:", pessoa.getResidencia());
                    panel.add(grid);
                })
                .adicionarBotoesPadrao(v -> editarPessoa(idUsuarioProvider),null);

            return builder.construirPainel();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return painelErro;
        }
    }
    
    private static void editarPessoa(Supplier<String> idUsuarioProvider) {
        String idUsuario = idUsuarioProvider.get();
        if (idUsuario != null && !idUsuario.isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame();
                frame.add(new FormPessoa(() -> idUsuario));
                frame.setSize(456, 600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        }
    }
}