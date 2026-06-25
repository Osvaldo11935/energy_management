package provedores;

import java.util.List;

import anotacoes.ComboDadosProvedor;
import anotacoes.ComboItem;
import modeloFiles.PerfilMenuFile;
import models.PerfilMenuModelo;
import models.UsuarioModelo;
import utils.Session;

public class MenuPerfilProvedor  implements ComboDadosProvedor {

    @Override
    public List<ComboItem> carregar() {

        UsuarioModelo usuarioLogado = Session.getUsuario();

        List<PerfilMenuModelo> menus = PerfilMenuFile.instaciar().buscarMenuPorPerfilId(usuarioLogado.getPerfilId());
        
        return menus.stream()
                     .filter(e -> e.getMenu().getCaminhoClasse() != null && e.getMenu().getCaminhoClasse() != "")
                     .map(e-> new ComboItem(e.getMenuId(), e.getMenu().getMenuPai().getNome() +" - "+ e.getMenu().getNome()))
                     .toList();
    }
}