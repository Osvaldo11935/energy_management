package visoes;

import java.awt.BorderLayout;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.*;
import modeloFiles.PessoaFile;
import modelos.PessoaModelo;


public class FormPessoaVisao extends JPanel {
    private Supplier<String> idUsuarioProvider;
    public FormPessoaVisao(Supplier<String> idUsuarioProvider) {
        this.idUsuarioProvider = idUsuarioProvider;
        setLayout(new BorderLayout());

        FormGerador form = new FormGerador(PessoaModelo.class);

        form.adicionarAcaoBotao("Salvar", obj -> {
            PessoaModelo formDados = (PessoaModelo) obj;
            
            List<PessoaModelo> pessoas = new PessoaFile(new PessoaModelo()).listar();
           
            int novoId = (pessoas == null || pessoas.isEmpty())? 1: pessoas.getLast().getId() + 1;
            String idUsuario = this.idUsuarioProvider.get();
            PessoaModelo novaPessoa = new PessoaModelo(
                novoId,
                formDados.getNumeroBI(),
                formDados.getNomeCompleto(),
                formDados.getNomePai(),
                formDados.getNomeMae(),
                formDados.getDataNascimento(),
                formDados.getEstadoCivil(),
                formDados.getGenero(),
                formDados.getResidencia(),
                formDados.getNaturalidade(),
                formDados.getDocumentoEmitidoEm(),
                formDados.getDocumentoValidoAte(),
                formDados.getAltura(),
                Integer.parseInt(idUsuario)
            );

            novaPessoa.salvarDados();
            
            JOptionPane.showMessageDialog(this, novaPessoa.toString());
        });

        add(form, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormPessoaVisao(() -> "").setVisible(true));
    }
}