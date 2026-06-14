package modeloFiles;
import java.io.*;
import javax.swing.*;
import java.util.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import models.CorteEnergiaModelo;

public class CorteEnergiaFile extends CrudFile<CorteEnergiaModelo> {

    public CorteEnergiaFile(CorteEnergiaModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/CorteEnergia.DAT", model, CorteEnergiaModelo.class);
    }

}