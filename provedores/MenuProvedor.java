package provedores;

import java.util.List;

import anotacoes.ComboDadosProvedor;
import anotacoes.ComboItem;
import modeloFiles.MenuFile;
import models.MenuModelo;

public class MenuProvedor  implements ComboDadosProvedor {

    @Override
    public List<ComboItem> carregar() {
        List<MenuModelo> menus = new MenuFile(new MenuModelo())
                                     .listar();
                        
        return menus.stream()
                     .map(e-> new ComboItem(e.getId(), e.getNome()))
                     .toList();
    }
}