package provedores;

import java.util.List;

import anotacoes.ComboDadosProvedor;
import anotacoes.ComboItem;

public class MunicipioProvedor implements ComboDadosProvedor{

    @Override
    public List<ComboItem> carregar() {
        return List.of(
            new ComboItem(1, "Luanda"),
            new ComboItem(2, "Bengo")
        );
    }
}
