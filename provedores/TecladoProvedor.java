package provedores;

import java.util.List;

import anotacoes.ComboDadosProvedor;
import anotacoes.ComboItem;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class TecladoProvedor implements ComboDadosProvedor {

    @Override
    public List<ComboItem> carregar() {

        List<ComboItem> itens = new ArrayList<>();

        List<String> modificadores = List.of(
            "",
            "Ctrl + ",
            "Alt + ",
            "Shift + ",
            "Ctrl + Shift + ",
            "Ctrl + Alt + ",
            "Alt + Shift + "
        );

        Field[] campos = KeyEvent.class.getFields();

        for (Field campo : campos) {

            if (!campo.getName().startsWith("VK_")) {
                continue;
            }

            try {

                int codigo = campo.getInt(null);

                String tecla =
                    KeyEvent.getKeyText(codigo);

                if (tecla == null ||
                    tecla.isBlank() ||
                    tecla.equalsIgnoreCase("Unknown keyCode: 0")) {
                    continue;
                }

                for (String mod : modificadores) {

                    itens.add(
                        new ComboItem(
                            mod + tecla,
                            mod + tecla,null
                        )
                    );
                }

            } catch (Exception ignored) {
            }
        }

        return itens;
    }
}