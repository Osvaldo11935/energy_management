package modeloFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modeloFiles.common.CrudFile;
import models.AtalhoModelo;

public class AtalhoFile extends CrudFile<AtalhoModelo> {

    public AtalhoFile(AtalhoModelo model) {
        super(System.getProperty("user.dir")+ "/data/Atalho.DAT",model, AtalhoModelo.class);
    }

    public static  AtalhoFile instaciar()
    {
        return new  AtalhoFile(new AtalhoModelo());
    }
    
    public List<AtalhoModelo> buscarAtalhoPorUsuarioId(int usuarioId) {

        List<AtalhoModelo> lista = new ArrayList<>();

        try {

            stream.seek(4);

            while (
                    stream.getFilePointer() < stream.length()) {

                AtalhoModelo Atalho = new AtalhoModelo();

                Atalho.read(stream);

                if (Atalho.getUsuarioId() == usuarioId && Atalho.isActivo()) {

                    lista.add(Atalho);
                }
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return lista;
    }
        
}