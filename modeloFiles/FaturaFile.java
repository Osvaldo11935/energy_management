package modeloFiles;
import java.io.*;
import javax.swing.*;
import java.util.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import models.FaturaModelo;

public class FaturaFile extends CrudFile<FaturaModelo> {

    public FaturaFile(FaturaModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Fatura.DAT", model, FaturaModelo.class);
    }

}