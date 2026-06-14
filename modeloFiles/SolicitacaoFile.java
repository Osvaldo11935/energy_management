package modeloFiles;

import java.io.*;
import java.util.*;
import javax.swing.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import models.SolicitacaoModelo;

public class SolicitacaoFile extends CrudFile<SolicitacaoModelo> {

    public SolicitacaoFile(SolicitacaoModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Solicitacao.DAT", model, SolicitacaoModelo.class);
    }
}