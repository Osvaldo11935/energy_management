package provedores;

import java.util.List;

import anotacoes.ComboDadosProvedor;
import anotacoes.ComboItem;
import modeloFiles.MenuFile;
import modelos.MenuModelo;

public class MenuProvedor  implements ComboDadosProvedor {

    @Override
    public List<ComboItem> carregar() {
        List<MenuModelo> menus = new MenuFile(new MenuModelo())
                                     .listar();
        //e.getMenuPaiId() > 0 ? e.getMenuPai().getNome() +" - "+  e.getNome() : e.getNome()
        return menus.stream()
                     .map(e-> new ComboItem(e.getId(), e.getNome(), e.getMenuPaiId() > 0 ? e.getMenuPai().getId(): 0))
                     .toList();
    }
}