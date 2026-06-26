package modeloFiles;
import java.io.*;
import java.util.*;
import javax.swing.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import modelos.ContadorModelo;
import modelos.PessoaModelo;

public class ContadorFile extends CrudFile<ContadorModelo> {

    public ContadorFile(ContadorModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Contador.DAT", model, ContadorModelo.class);
    }

    public ContadorModelo buscarPorClienteId(int clienteId) {
        try {
            stream.seek(4);

            while (stream.getFilePointer() < stream.length()) {

                long pos = stream.getFilePointer();

                ContadorModelo contador = new ContadorModelo();
                contador.read(stream);

                if (contador.getClienteId() == clienteId && contador.isActivo()) {
                    return contador;
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar contador");
        }

        return null;
    }
    
    public static ContadorFile instaciar()
    {
        return new ContadorFile(new ContadorModelo());
    }
}