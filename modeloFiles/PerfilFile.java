package modeloFiles;

import java.io.*;
import java.util.*;
import javax.swing.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import models.MenuModelo;
import models.PerfilModelo;

public class PerfilFile  extends CrudFile<PerfilModelo> {

    public PerfilFile(PerfilModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Perfil.DAT", model, PerfilModelo.class);
    }

    public static  PerfilFile instaciar()
    {
        return new  PerfilFile(new  PerfilModelo());
    }
}