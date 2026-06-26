package modeloFiles;
import java.io.*;
import javax.swing.*;
import java.util.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import modelos.PagamentoModelo;

public class PagamentoFile extends CrudFile<PagamentoModelo> {

    public PagamentoFile(PagamentoModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Pagamento.DAT", model, PagamentoModelo.class);
    }

    public static  PagamentoFile instaciar()
    {
        return new  PagamentoFile(new  PagamentoModelo());
    }
}