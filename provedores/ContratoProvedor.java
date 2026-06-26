package provedores;

import java.util.List;

import anotacoes.ComboDadosProvedor;
import anotacoes.ComboItem;
import modeloFiles.ClienteFile;
import modelos.UsuarioModelo;
import utils.Session;

public class ContratoProvedor implements ComboDadosProvedor {
    UsuarioModelo usuarioLogado = Session.getUsuario();
    @Override
    public List<ComboItem> carregar() {
        return ClienteFile.instaciar().listar()
                                      .stream()
                                      .filter(e -> e.getUsuarioId() == usuarioLogado.getId())
                                      .map(e -> new ComboItem(e.getId(), e.getId() + "-" + e.getTipoContrato(), null))
                                      .toList();
    }
}
