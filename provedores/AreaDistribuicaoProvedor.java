package provedores;

import java.util.*;
import anotacoes.*;
import modeloFiles.AreaDistribuicaoFile;
import modelos.AreaDistribuicaoModelo;

public class AreaDistribuicaoProvedor implements ComboDadosProvedor{
    @Override
    public List<ComboItem> carregar() {

        List<AreaDistribuicaoModelo> areDistribuicoes = AreaDistribuicaoFile
                                     .instaciar()
                                     .listar();

        return areDistribuicoes.stream()
                     .map(e-> new ComboItem(e.getId(), e.getProvincia()+ "-" + e.getBairro(), null))
                     .toList();
    }
}
