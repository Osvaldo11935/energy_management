package modeloFiles;

import java.io.*;
import java.util.*;
import javax.swing.*;
import models.TarifaModelo;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
public class TarifaFile extends CrudFile<TarifaModelo> {

    public TarifaFile(TarifaModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Tarifa.DAT", model, TarifaModelo.class);
    }
}