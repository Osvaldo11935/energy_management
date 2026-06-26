package modeloFiles;

import javax.swing.*;
import java.io.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import modelos.AreaDistribuicaoModelo;

import java.util.*;

public class AreaDistribuicaoFile extends CrudFile<AreaDistribuicaoModelo> {

    public AreaDistribuicaoFile(AreaDistribuicaoModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/AreaDistribuicao.DAT", model, AreaDistribuicaoModelo.class);
    }

    public static AreaDistribuicaoFile instaciar()
    {
        return new AreaDistribuicaoFile(new AreaDistribuicaoModelo());
    }
}