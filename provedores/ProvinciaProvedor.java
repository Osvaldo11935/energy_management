package provedores;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import SwingComponents.*;
import anotacoes.ComboDadosProvedor;
import anotacoes.ComboItem;

public class ProvinciaProvedor implements ComboDadosProvedor {

    @Override
    public List<ComboItem> carregar() {
    

     List<Tabela3_2> provinciasss =
            ProvinciaProvedor.carregardddcc(
                System.getProperty("user.dir")
                + "/data/Municipio.tab");

        for (Tabela3_2 provincia : provinciasss) {

            System.out.println(
                 provincia.get_Codigo_Pai()
                 + "-"
                +provincia.get_Codigo()
                + " - "
                + provincia.get_Designacao());
        }

        List<Tabela2> provincias =
            ProvinciaProvedor.carregarddd(
                System.getProperty("user.dir")
                + "/data/Provincia.tab");

        for (Tabela2 provincia : provincias) {

            System.out.println(
                provincia.get_Codigo()
                + " - "
                + provincia.get_Designacao());
        }
  
        return List.of(
                new ComboItem(1,"Bengo"),
                new ComboItem(2,"Benguela"),
                new ComboItem(3,"Bie"),
                new ComboItem(4,"Luanda")
        );
    }

        public static List<Tabela2> carregarddd(String ficheiro) {

        List<Tabela2> lista = new ArrayList<>();

        try (ObjectInputStream in =
                new ObjectInputStream(
                        new FileInputStream(ficheiro))) {

            // Nome do arquivo gravado
            String nomeArquivo = StrStream.Read_String(in);

            // Próximo código
            int proximoCodigo = in.readInt();

            // Quantidade de registros
            int quantidade = in.readInt();

            for (int i = 0; i < quantidade; i++) {

                Object obj = in.readObject();

                if (obj instanceof Tabela2 tabela) {
                    lista.add(tabela);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static List<Tabela3_2> carregardddcc(String ficheiro) {

        List<Tabela3_2> lista = new ArrayList<>();

        try (ObjectInputStream in =
                new ObjectInputStream(
                        new FileInputStream(ficheiro))) {

            // Nome do arquivo gravado
            String nomeArquivo = StrStream.Read_String(in);

            // Próximo código
            int proximoCodigo = in.readInt();

            // Quantidade de registros
            int quantidade = in.readInt();

            for (int i = 0; i < quantidade; i++) {

                Object obj = in.readObject();

                if (obj instanceof Tabela3_2 tabela) {
                    lista.add(tabela);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
