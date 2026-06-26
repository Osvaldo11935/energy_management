package provedores;

import java.util.List;

import anotacoes.ComboDadosProvedor;
import anotacoes.ComboItem;
import modeloFiles.PerfilFile;
import modelos.PerfilModelo;
import modelos.UsuarioModelo;
import utils.Session;

public class PerfilProvedor implements ComboDadosProvedor {

    @Override
    public List<ComboItem> carregar() {

        List<PerfilModelo> perfis = new PerfilFile(new PerfilModelo())
                                       .listar();

        UsuarioModelo usuarioLogado = Session.getUsuario();

        if(Session.estaLogado() && usuarioLogado.getPerfil().getNome().toLowerCase().equals("admin"))
        {
            return perfis.stream()
                     .filter(e -> e.getNome().toLowerCase().equals("cliente"))
                     .map(e-> new ComboItem(e.getId(), e.getNome(), null))
                     .toList();
        }
        else
        {
            return perfis.stream()
                     .map(e-> new ComboItem(e.getId(), e.getNome(), null))
                     .toList();
        }
    }
}