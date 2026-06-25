package utils;

public class DataMapper {

    public static String normalizarData(String data) {

        if (data.matches("\\d{2}/[A-Za-z]{3}/\\d{4}")) {
            return data;
        }

        String[] meses = {
            "Jan", "Fev", "Mar", "Abr",
            "Mai", "Jun", "Jul", "Ago",
            "Set", "Out", "Nov", "Dez"
        };

        String[] partes = data.split("[-/]");

        if (partes.length != 3) {
            return data;
        }

        String dia = partes[0];
        String mes = partes[1];
        String ano = partes[2];

        if (!mes.matches("\\d+")) {
            return dia + "/" + mes + "/" + ano;
        }

        int numeroMes = Integer.parseInt(mes);

        if (numeroMes < 1 || numeroMes > 12) {
            return data;
        }

        return dia + "/" + meses[numeroMes - 1] + "/" + ano;
    }
}
