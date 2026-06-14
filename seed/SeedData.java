package seed;

import java.util.*;
import modeloFiles.*;
import models.*;

public class SeedData {

    public static void executar() {
        criarPerfilAdmin();
        criarMenus();
        criarUsuarioAdmin();
        relacionarMenusPerfil();
        System.out.println("Seed executado.");
    }


    private static void criarPerfilAdmin() {

        PerfilFile file =
                new PerfilFile(
                        new PerfilModelo());

        List<PerfilModelo> perfis =
                file.listar();

        if (!perfis.isEmpty())
            return;

        new PerfilModelo(1,"Administrador","Acesso total").salvarDados();
        new PerfilModelo(2,"Cliente","Acesso Parcial").salvarDados();
    }


    private static void criarUsuarioAdmin() {

        UsuarioFile file =new UsuarioFile(new UsuarioModelo());

        if (!file.listar().isEmpty())
            return;

        UsuarioModelo admin = new UsuarioModelo(1, "admin", "admin@gmail.com", "999999991", "123", 1);
        admin.salvarDados();
    }

    private static void criarMenus() {

        MenuFile file =
                new MenuFile(
                        new MenuModelo());

        if (!file.listar().isEmpty())
            return;

        MenuModelo menuCadastros = new MenuModelo(1,0,"CAD","Cadastros","Menu principal","","",1,1);

        menuCadastros.salvarDados();

        MenuModelo menuUsuarios =
                new MenuModelo(2, 1,"USR","Usuários","Gestão de usuários","","telas.TabelaUsuarios",1,1);

        menuUsuarios.salvarDados();

        MenuModelo menuPerfis =
                new MenuModelo(3,1,"PER","Perfis","Gestão de perfis","","telas.TabelaPerfil",2,1);
 
        menuPerfis.salvarDados();

        MenuModelo menuPerfilMenu =
                new MenuModelo(4,1,"REL","Relacionar Menus","Permissões","","telas.TabelaPerfilMenu",3,1);

        menuPerfilMenu.salvarDados();

        MenuModelo menuClientes =
                new MenuModelo(5,1,"CLI","Clientes","Cadastro clientes","","telas.TabelaClientes",4,1);

        menuClientes.salvarDados();
        
        MenuModelo menuMenu =
                new MenuModelo(6,1,"MEN","Menus","Gestão de menus","","telas.TabelaMenu",5,1);

        menuMenu.salvarDados();
    }

    private static void relacionarMenusPerfil() {

        PerfilMenuFile file = new PerfilMenuFile(new PerfilMenuModelo());

        if (!file.listar().isEmpty())
            return;

        MenuFile menuFile = new MenuFile(new MenuModelo());

        List<MenuModelo> menus = menuFile.listar();

        for (MenuModelo menu : menus) {

            PerfilMenuModelo rel =
                    new PerfilMenuModelo(0,1, menu.getId(), true,true,true,true);
                    
            rel.salvarDados();
        }
    }
    public static void main(String[] args)
    {
        SeedData.executar();
    }
}