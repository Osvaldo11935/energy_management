package modeloFiles;

import java.io.*;
import java.util.*;
import javax.swing.*;

import SwingComponents.*;
import modeloFiles.common.CrudFile;
import modelos.TarifaModelo;
public class TarifaFile extends CrudFile<TarifaModelo> {

    public TarifaFile(TarifaModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Tarifa.DAT", model, TarifaModelo.class);
    }
    public static  TarifaFile instaciar()
    {
        return new  TarifaFile(new  TarifaModelo());
    }
}