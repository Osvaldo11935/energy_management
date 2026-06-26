package modeloFiles;

import java.io.*;
import java.util.*;
import javax.swing.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import modelos.PessoaModelo;

public class PessoaFile  extends CrudFile<PessoaModelo> {

    public PessoaFile(PessoaModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Pessoa.DAT", model, PessoaModelo.class);
    }

    public PessoaModelo buscarPorUsuarioId(int usuarioId) {
        try {
            stream.seek(4);

            while (stream.getFilePointer() < stream.length()) {

                long pos = stream.getFilePointer();

                PessoaModelo pessoa = new PessoaModelo();
                pessoa.read(stream);

                if (pessoa.getUsuarioId() == usuarioId) {
                    return pessoa;
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar pessoa");
        }

        return null;
    }
    public static  PessoaFile instaciar()
    {
        return new  PessoaFile(new  PessoaModelo());
    }
}
