package modeloFiles;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import java.util.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import models.LeituraConsumoModelo;

public class LeituraConsumoFile extends CrudFile<LeituraConsumoModelo> {

    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/M/d");
    public LeituraConsumoFile(LeituraConsumoModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/LeituraConsumo.DAT", model, LeituraConsumoModelo.class);
    }

    public static LeituraConsumoFile instaciar()
    {
        return new LeituraConsumoFile(new LeituraConsumoModelo());
    }
    
    public List<LeituraConsumoModelo> buscarPorContadorId(int contadorId) {

        List<LeituraConsumoModelo> resultado = new ArrayList<>();

        for (LeituraConsumoModelo leitura : listar()) {

            if (leitura.getContadorId() == contadorId) {
                resultado.add(leitura);
            }
        }

        return resultado;
    }

    public LeituraConsumoModelo buscarUltimaLeitura(int contadorId) {

        List<LeituraConsumoModelo> leituras = buscarPorContadorId(contadorId);

        if (leituras.isEmpty()) {
            return null;
        }

        LeituraConsumoModelo ultima = leituras.get(0);

        for (LeituraConsumoModelo leitura : leituras) {
          
            LocalDate dataActual =
                    LocalDate.parse(leitura.getDataLeitura(), formato);

            LocalDate dataUltima = LocalDate.parse(ultima.getDataLeitura(), formato);

            if (dataActual.isAfter(dataUltima)) {
                ultima = leitura;
            }
        }

        return ultima;
    }

    public LeituraConsumoModelo buscarLeituraAnterior(
            int contadorId,
            String dataReferencia
    ) {

        List<LeituraConsumoModelo> leituras =
                buscarPorContadorId(contadorId);

        LocalDate referencia =
                LocalDate.parse(dataReferencia, formato);

        LeituraConsumoModelo resultado = null;

        for (LeituraConsumoModelo leitura : leituras) {

            LocalDate data =
                    LocalDate.parse(leitura.getDataLeitura(), formato);

            if (data.isBefore(referencia)) {

                if (
                    resultado == null ||
                    data.isAfter(LocalDate.parse(resultado.getDataLeitura(), formato))
                ) {
                    resultado = leitura;
                }
            }
        }

        return resultado;
    }

    public List<LeituraConsumoModelo> buscarPorPeriodo(
            String inicio,
            String fim
    ) {

        LocalDate dataInicio =
                LocalDate.parse(inicio, formato);

        LocalDate dataFim =
                LocalDate.parse(fim, formato);

        List<LeituraConsumoModelo> resultado =
                new ArrayList<>();

        for (LeituraConsumoModelo leitura : listar()) {

            LocalDate data =
                    LocalDate.parse(leitura.getDataLeitura(), formato);

            if (
                !data.isBefore(dataInicio)
                &&
                !data.isAfter(dataFim)
            ) {
                resultado.add(leitura);
            }
        }

        return resultado;
    }
}