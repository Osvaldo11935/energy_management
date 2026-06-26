package provedores;

import java.util.List;

import anotacoes.ComboDadosProvedor;
import anotacoes.ComboItem;
import modeloFiles.UsuarioFile;

public class ClienteProvedor  implements ComboDadosProvedor {

    @Override
    public List<ComboItem> carregar() {
        return UsuarioFile.instaciar().listar()
                                      .stream()
                                      .filter(e -> e.ehCliente())
                                      .map(e -> new ComboItem(e.getId(), e.getNomeUsuario(), null))
                                      .toList();
    }
}