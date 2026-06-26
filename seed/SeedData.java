package seed;

import java.util.*;
import modeloFiles.*;
import modelos.*;

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

        new PerfilModelo("Administrador","Acesso total").salvarDados();
        new PerfilModelo("Cliente","Acesso Parcial").salvarDados();
    }


    private static void criarUsuarioAdmin() {

        UsuarioFile file =new UsuarioFile(new UsuarioModelo());

        if (!file.listar().isEmpty())
            return;

        UsuarioModelo admin = new UsuarioModelo(1, "admin", "admin@gmail.com", "999999991", "123", 1);
        admin.salvarDados();
    }

    private static void criarMenus() {

        MenuFile file = new MenuFile(new MenuModelo());

        if (!file.listar().isEmpty())
            return;

        new MenuModelo(2, 0, "USR", "Usuários", "", "funcionario32.png", "", 1, 1).salvarDados();

        new MenuModelo(20, 2, "USR_NEW", "Novo", "", "", "telas.FormWizard", 1, 1).salvarDados();
        new MenuModelo(21, 2, "USR_LIST", "Pesquisar", "", "", "telas.TabelaUsuarios", 2, 1).salvarDados();

        new MenuModelo(3, 0, "PER", "Perfis", "", "funcionario32.png", "", 2, 1).salvarDados();

        new MenuModelo(30, 3, "PER_NEW", "Novo", "", "", "telas.FormPerfil", 1, 1).salvarDados();
        new MenuModelo(31, 3, "PER_LIST", "Pesquisar", "", "", "telas.TabelaPerfil", 2, 1).salvarDados();

        new MenuModelo(5, 0, "CLI", "Clientes", "", "funcionario32.png", "", 3, 1).salvarDados();

        new MenuModelo(50, 5, "CLI_NEW", "Novo", "", "", "telas.FormCliente", 1, 1).salvarDados();
        new MenuModelo(51, 5, "CLI_LIST", "Pesquisar", "", "", "telas.TabelaClientes", 2, 1).salvarDados();

        new MenuModelo(6, 0, "MEN", "Menus", "", "funcionario32.png", "", 4, 1).salvarDados();

        new MenuModelo(60, 6, "MEN_NEW", "Novo", "", "", "telas.FormMenu", 1, 1).salvarDados();
        new MenuModelo(61, 6, "MEN_LIST", "Pesquisar", "", "", "telas.TabelaMenu", 2, 1).salvarDados();
        new MenuModelo(62, 6, "MEN_PER_LIST", "Perfil Menu", "", "", "telas.TabelaPerfilMenu", 2, 1).salvarDados();

        MenuModelo rede = new MenuModelo(10,0,"RED","Gestão de Rede Elétrica","","funcionario32.png","",10,1);
        rede.salvarDados();

        MenuModelo sub = new MenuModelo(7,10,"SUB","Subestações","", "","",1,1);
        sub.salvarDados();

        new MenuModelo( 70,7,"SUB_NEW","Novo","","","telas.FormSubestacao",1,1).salvarDados();
        new MenuModelo(71,7, "SUB_LIST","Pesquisar", "","","telas.TabelaSubestacao",2,1).salvarDados();

        MenuModelo area =new MenuModelo(8,10,"ARE","Área Distribuição","","","", 2,1);
        area.salvarDados();

        new MenuModelo( 80, 8,"ARE_NEW","Novo","", "","telas.FormAreaDistribuicao",1,1).salvarDados();
        new MenuModelo( 81, 8,"ARE_LIST", "Pesquisar","","","telas.TabelaAreaDistribuicao",2,1).salvarDados();
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