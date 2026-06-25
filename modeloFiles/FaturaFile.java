package modeloFiles;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import java.util.*;
import SwingComponents.*;
import enums.EstadoFatura;
import modeloFiles.common.CrudFile;
import models.ClienteModelo;
import models.ContadorModelo;
import models.FaturaModelo;

public class FaturaFile extends CrudFile<FaturaModelo> {

    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/M/d");

    public FaturaFile(FaturaModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Fatura.DAT", model, FaturaModelo.class);
    }
    
    public static FaturaFile instaciar()
    {
        return new FaturaFile(new FaturaModelo());
    }
    
    public List<FaturaModelo> buscarPorClienteId(int clienteId) {

        List<FaturaModelo> lista = new ArrayList<>();

        try {

            stream.seek(4);

            while (
                stream.getFilePointer() < stream.length()) {

                FaturaModelo fatura = new FaturaModelo();

                fatura.read(stream);
                if (fatura.getClienteId() == clienteId && fatura.isActivo()) {

                    lista.add(fatura);
                }
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return lista;
    }
        
    public List<FaturaModelo> buscarVencidasNaoPagas() {

        List<FaturaModelo> lista = new ArrayList<>();

        LocalDate hoje = LocalDate.now();
        try
        {
            stream.seek(4);

            while (stream.getFilePointer() < stream.length()) {

                FaturaModelo fatura = new FaturaModelo();

                fatura.read(stream);
                
                boolean naoPaga = !EstadoFatura.PAGO.toString().equals(fatura.getStatus());

                boolean naoCancelada = !EstadoFatura.CANCELADO.toString().equals(fatura.getStatus());
                
                if (naoPaga && naoCancelada && fatura.isActivo() && fatura.getId() > 0) {
                    LocalDate vencimento = LocalDate.parse(fatura.getDataVencimento(), formato);
                    if(vencimento.isBefore(hoje))
                       lista.add(fatura);
                }
            }
        }
        catch(IOException ex)
        {
           ex.printStackTrace();
        }

        return lista;
    }

    public List<FaturaModelo> buscarFaturasComAtrasoSuperior(int dias) {

        List<FaturaModelo> resultado = new ArrayList<>();

        LocalDate dataLimite = LocalDate.now().minusDays(dias);

        for (FaturaModelo f : listar()) {

            boolean naoPaga = !EstadoFatura.PAGO.toString().equals(f.getStatus());

            LocalDate vencimento = LocalDate.parse(f.getDataVencimento(), formato);

            if (naoPaga && vencimento.isBefore(dataLimite)) {
                resultado.add(f);
            }
        }
        return resultado;
    }
    
    public boolean existePorLeituraId(int leituraId) {
        for (FaturaModelo f : listar()) {
            if (leituraId == f.getLeituraId()) {
                return true;
            }
        }
        return false;
    }
}