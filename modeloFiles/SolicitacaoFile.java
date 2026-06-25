package modeloFiles;

import java.io.*;
import java.util.*;
import javax.swing.*;
import SwingComponents.*;
import modeloFiles.common.CrudFile;
import models.SolicitacaoModelo;

public class SolicitacaoFile extends CrudFile<SolicitacaoModelo> {

    public SolicitacaoFile(SolicitacaoModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Solicitacao.DAT", model, SolicitacaoModelo.class);
    }
    public static  SolicitacaoFile instaciar()
    {
        return new  SolicitacaoFile(new  SolicitacaoModelo());
    }
    
    public List<SolicitacaoModelo> buscarPorUsuarioId(int usuarioId) {

        List<SolicitacaoModelo> lista = new ArrayList<>();

        try {

            stream.seek(4);

            while (
                    stream.getFilePointer() < stream.length()) {

                SolicitacaoModelo solicitacao = new SolicitacaoModelo();

                solicitacao.read(stream);

                if (solicitacao.getUsuarioId() == usuarioId && solicitacao.isActivo()) {

                    lista.add(solicitacao);
                }
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return lista;
    }
    
    public List<SolicitacaoModelo> buscarPorTecnicoResponsavelId(int tecnicoId) {

        List<SolicitacaoModelo> lista = new ArrayList<>();

        try {

            stream.seek(4);

            while (
                    stream.getFilePointer() < stream.length()) {

                SolicitacaoModelo solicitacao = new SolicitacaoModelo();

                solicitacao.read(stream);

                if (solicitacao.getTecnicoResponsavelId() == tecnicoId && solicitacao.isActivo()) {

                    lista.add(solicitacao);
                }
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return lista;
    }

    public List<SolicitacaoModelo> buscarPorSolicitacaoPaiId(int solicitacaoPaiId) {

        List<SolicitacaoModelo> lista = new ArrayList<>();

        try {

            stream.seek(4);

            while (
                    stream.getFilePointer() < stream.length()) {

                SolicitacaoModelo solicitacao = new SolicitacaoModelo();

                solicitacao.read(stream);

                if (solicitacao.getSolicitacaoPaiId() == solicitacaoPaiId && solicitacao.isActivo()) {

                    lista.add(solicitacao);
                }
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return lista;
    }
}