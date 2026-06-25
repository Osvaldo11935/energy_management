package provedores;

import java.util.List;

import anotacoes.ComboDadosProvedor;
import anotacoes.ComboItem;
import modeloFiles.UsuarioFile;

public class TecnicosProvedor  implements ComboDadosProvedor {

    @Override
    public List<ComboItem> carregar() {
        return UsuarioFile.instaciar().listar()
                                      .stream()
                                      .filter(e -> e.ehTecnico())
                                      .map(e -> new ComboItem(e.getId(), e.getNomeUsuario()))
                                      .toList();
    }
}
