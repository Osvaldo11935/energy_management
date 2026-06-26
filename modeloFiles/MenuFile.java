package modeloFiles;

import modeloFiles.common.CrudFile;
import modelos.MenuModelo;

public class MenuFile extends CrudFile<MenuModelo> {

    public MenuFile(MenuModelo model) {
        super(System.getProperty("user.dir")+ "/data/Menu.DAT",model, MenuModelo.class);
    }

    public static  MenuFile instaciar()
    {
        return new  MenuFile(new  MenuModelo());
    }
}