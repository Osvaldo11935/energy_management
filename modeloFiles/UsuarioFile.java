package modeloFiles;

import SwingComponents.*;
import modeloFiles.common.CrudFile;
import models.UsuarioModelo;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class UsuarioFile extends CrudFile<UsuarioModelo> {

    public UsuarioFile(UsuarioModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Usuario.DAT", model, UsuarioModelo.class);
    }
        
    public UsuarioModelo autenticar(String login, String password) {

        try {
            stream.seek(4);

            while (stream.getFilePointer() < stream.length()) {

                UsuarioModelo usuario = new UsuarioModelo();
                usuario.read(stream);

                boolean loginValido =
                        usuario.getNomeUsuario().equalsIgnoreCase(login)
                        || usuario.getEmail().equalsIgnoreCase(login);

                boolean senhaValida =
                        usuario.getPalavraPass().equals(password);

                if (loginValido && senhaValida) {
                    return usuario;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro ao tentar autenticar");
        }

        return null;
    }
}