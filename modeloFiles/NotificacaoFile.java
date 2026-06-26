package modeloFiles;
import java.io.*;
import javax.swing.*;
import java.util.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import modelos.NotificacaoModelo;

public class NotificacaoFile extends CrudFile<NotificacaoModelo> {

    public NotificacaoFile(NotificacaoModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Notificacao.DAT", model, NotificacaoModelo.class);
    }
    public static  NotificacaoFile instaciar()
    {
        return new  NotificacaoFile(new  NotificacaoModelo());
    }

    
    public List<NotificacaoModelo> buscarPorClienteId(int clienteId) {

        List<NotificacaoModelo> lista = new ArrayList<>();

        try {

            stream.seek(4);

            while (
                    stream.getFilePointer() < stream.length()) {

                NotificacaoModelo notificacao = new NotificacaoModelo();

                notificacao.read(stream);

                if (notificacao.getClienteId() == clienteId && notificacao.isActivo()) {

                    lista.add(notificacao);
                }
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return lista;
    }
}