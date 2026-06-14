package modeloFiles;
import java.io.*;
import javax.swing.*;
import java.util.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import models.LeituraConsumoModelo;

public class LeituraConsumoFile extends CrudFile<LeituraConsumoModelo> {

    public LeituraConsumoFile(LeituraConsumoModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/LeituraConsumo.DAT", model, LeituraConsumoModelo.class);
    }

}