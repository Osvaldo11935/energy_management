package provedores;

import java.util.List;

import anotacoes.ComboDadosProvedor;
import anotacoes.ComboItem;

public class BoleanoProvedor implements ComboDadosProvedor{

    @Override
    public List<ComboItem> carregar() {
        return List.of(
            new ComboItem(true, "Sim"),
            new ComboItem(false, "Não")
        );
    }
}
