package provedores;
import java.util.*;
import anotacoes.*;

public class UsuarioProvedor  implements ComboDadosProvedor {

    @Override
    public List<ComboItem> carregar() {
         return List.of(
                new ComboItem(1,"Bengo",null),
                new ComboItem(2,"Benguela",null),
                new ComboItem(3,"Bie",null),
                new ComboItem(4,"Luanda",null)
        );
    }
}
