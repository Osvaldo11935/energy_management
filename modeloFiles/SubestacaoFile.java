package modeloFiles;
import javax.swing.*;
import java.io.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import models.SubestacaoModelo;
import java.util.*;

public class SubestacaoFile extends CrudFile<SubestacaoModelo> {

    public SubestacaoFile(SubestacaoModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Subestacao.DAT", model, SubestacaoModelo.class);
    }

    public static  SubestacaoFile instaciar()
    {
        return new  SubestacaoFile(new  SubestacaoModelo());
    }
}