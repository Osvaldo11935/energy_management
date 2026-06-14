package modeloFiles;

import modeloFiles.common.CrudFile;
import models.MenuModelo;

public class MenuFile extends CrudFile<MenuModelo> {

    public MenuFile(MenuModelo model) {
        super(System.getProperty("user.dir")+ "/data/Menu.DAT",model, MenuModelo.class);
    }
}