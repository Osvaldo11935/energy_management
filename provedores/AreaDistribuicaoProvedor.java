package provedores;

import java.util.*;
import anotacoes.*;
import modeloFiles.AreaDistribuicaoFile;
import models.AreaDistribuicaoModelo;

public class AreaDistribuicaoProvedor implements ComboDadosProvedor{
    @Override
    public List<ComboItem> carregar() {

        List<AreaDistribuicaoModelo> areDistribuicoes = new AreaDistribuicaoFile(new AreaDistribuicaoModelo())
                                     .listar();

        return areDistribuicoes.stream()
                     .map(e-> new ComboItem(e.getId(), e.getProvincia()+ "-" + e.getBairro()))
                     .toList();
    }
}
