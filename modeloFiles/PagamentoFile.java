package modeloFiles;
import java.io.*;
import javax.swing.*;
import java.util.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import models.PagamentoModelo;

public class PagamentoFile extends CrudFile<PagamentoModelo> {

    public PagamentoFile(PagamentoModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Pagamento.DAT", model, PagamentoModelo.class);
    }

}