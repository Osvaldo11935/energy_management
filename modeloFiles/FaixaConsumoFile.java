package modeloFiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modeloFiles.common.CrudFile;
import models.ClienteModelo;
import models.FaixaConsumoModelo;

public class FaixaConsumoFile extends CrudFile<FaixaConsumoModelo> {

    public FaixaConsumoFile(FaixaConsumoModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/FaixaConsumo.DAT", model, FaixaConsumoModelo.class);
    }

    public static FaixaConsumoFile instaciar()
    {
        return new FaixaConsumoFile(new FaixaConsumoModelo());
    }

    public List<FaixaConsumoModelo> buscarPorTarifaId(int tarifaId) {

        List<FaixaConsumoModelo> lista = new ArrayList<>();

        try {

            stream.seek(4);

            while (
                    stream.getFilePointer() < stream.length()) {

                FaixaConsumoModelo faixaConsumo = new FaixaConsumoModelo();

                faixaConsumo.read(stream);

                if (faixaConsumo.getTarifaId() == tarifaId && faixaConsumo.isActivo()) {

                    lista.add(faixaConsumo);
                }
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return lista;
    }
       
}