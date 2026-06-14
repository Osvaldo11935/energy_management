package modeloFiles;
import java.io.*;
import java.util.*;
import javax.swing.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import models.ContadorModelo;

public class ContadorFile extends CrudFile<ContadorModelo> {

    public ContadorFile(ContadorModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Contador.DAT", model, ContadorModelo.class);
    }

}