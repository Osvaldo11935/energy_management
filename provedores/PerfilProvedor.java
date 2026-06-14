package provedores;

import java.util.List;

import anotacoes.ComboDadosProvedor;
import anotacoes.ComboItem;
import modeloFiles.PerfilFile;
import models.PerfilModelo;

public class PerfilProvedor implements ComboDadosProvedor {

    @Override
    public List<ComboItem> carregar() {
        List<PerfilModelo> perfis = new PerfilFile(new PerfilModelo())
                                     .listar();
        return perfis.stream()
                     .map(e-> new ComboItem(e.getId(), e.getNome()))
                     .toList();
    }
}