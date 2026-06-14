package provedores;

import java.util.List;

import anotacoes.ComboDadosProvedor;
import anotacoes.ComboItem;

public class ProvinciaProvedor implements ComboDadosProvedor {

    @Override
    public List<ComboItem> carregar() {
         return List.of(
                new ComboItem(1,"Bengo"),
                new ComboItem(2,"Benguela"),
                new ComboItem(3,"Bie"),
                new ComboItem(4,"Luanda")
        );
    }
}
