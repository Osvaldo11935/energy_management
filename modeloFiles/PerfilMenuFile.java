package modeloFiles;

import java.io.*;
import java.util.*;
import javax.swing.*;

import modeloFiles.common.CrudFile;
import models.MenuModelo;
import models.PerfilMenuModelo;


public class PerfilMenuFile extends CrudFile<PerfilMenuModelo> {

    public PerfilMenuFile(PerfilMenuModelo model) {
        super(System.getProperty("user.dir") + "/data/PerfilMenu.DAT", model,PerfilMenuModelo.class);
    }

    public List<PerfilMenuModelo> buscarMenuPorPerfilId(int perfilId) {

        List<PerfilMenuModelo> lista =
                new ArrayList<>();

        try {

            stream.seek(4);

            while (
                    stream.getFilePointer() < stream.length()) {

                PerfilMenuModelo perfil = new PerfilMenuModelo();

                perfil.read(stream);

                if (perfil.getPerfilId() == perfilId && perfil.isActivo()) {

                    lista.add(perfil);
                }
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return lista;
    }
    public static  PerfilMenuFile instaciar()
    {
        return new  PerfilMenuFile(new  PerfilMenuModelo());
    }
}