package modeloFiles;
import java.io.*;
import javax.swing.*;
import java.util.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import models.NotificacaoModelo;

public class NotificacaoFile extends CrudFile<NotificacaoModelo> {

    public NotificacaoFile(NotificacaoModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Notificacao.DAT", model, NotificacaoModelo.class);
    }

}