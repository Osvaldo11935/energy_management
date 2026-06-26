package provedores;

import java.util.*;
import anotacoes.*;
import modeloFiles.SubestacaoFile;
import modelos.SubestacaoModelo;

public class SubestacaoProvedor implements ComboDadosProvedor {

    @Override
    public List<ComboItem> carregar() {
        List<SubestacaoModelo> subestacoes = new SubestacaoFile(new SubestacaoModelo())
                                     .listar();
        return subestacoes.stream()
                     .map(e-> new ComboItem(e.getId(), e.getCodigo()+ "-" + e.getNome(),null))
                     .toList();
    }
}