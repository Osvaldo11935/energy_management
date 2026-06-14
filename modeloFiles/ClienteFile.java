package modeloFiles;
import javax.swing.*;
import java.io.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;

import java.util.*;
import models.ClienteModelo;
public class ClienteFile  extends CrudFile<ClienteModelo> {

    public ClienteFile(ClienteModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Cliente.DAT", model, ClienteModelo.class);
    }

}