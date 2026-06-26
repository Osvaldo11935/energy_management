package modeloFiles;
import javax.swing.*;
import java.io.*;
import SwingComponents.*;
import enums.TipoContratoEnum;
import enums.TipoNotificacaoEnum;
import modeloFiles.common.CrudFile;
import modelos.ClienteModelo;
import modelos.CorteEnergiaModelo;
import modelos.FaixaConsumoModelo;
import modelos.NotificacaoModelo;

import java.util.*;

public class ClienteFile  extends CrudFile<ClienteModelo> {

    public ClienteFile(ClienteModelo model)
    {
        super(System.getProperty("user.dir")+ "/data/Cliente.DAT", model, ClienteModelo.class);
    }

    public static ClienteFile instaciar()
    {
        return new ClienteFile(new ClienteModelo());
    }
    
    public void marcarParaCorte(int clienteId) {
        List<NotificacaoModelo> notificacao = NotificacaoFile.instaciar().listar();

        List<CorteEnergiaModelo> cortesEnergia = CorteEnergiaFile.instaciar().listar();

        int novoId = (cortesEnergia == null || cortesEnergia.isEmpty())? 1: cortesEnergia.getLast().getId() + 1;

        CorteEnergiaModelo corteEnergia = new CorteEnergiaModelo(
            novoId,
            clienteId,
            "Falta de pagamento - " + new Date()
        );
        
        corteEnergia.salvarDados();
        
        int novoNotificacaoId = (notificacao == null || notificacao.isEmpty())? 1: notificacao.getLast().getId() + 1;

        NotificacaoModelo notificar = new NotificacaoModelo(
            novoNotificacaoId,
            clienteId,
            0,
            "Aviso de Corte de Energia",
            "Informamos que existem faturas em atraso associadas ao seu contrato. Caso o débito não seja regularizado, o fornecimento de energia poderá ser interrompido conforme as condições do serviço.",
            TipoNotificacaoEnum.CORTE.toString()
        );
        notificar.salvarDados();
    }

    public List<ClienteModelo> buscarClientePorUsuarioId(int usuarioId) {

        List<ClienteModelo> lista = new ArrayList<>();

        try {

            stream.seek(4);

            while (
                    stream.getFilePointer() < stream.length()) {

                ClienteModelo cliente = new ClienteModelo();

                cliente.read(stream);

                if (cliente.getUsuarioId() == usuarioId && cliente.isActivo()) {

                    lista.add(cliente);
                }
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return lista;
    }

    public List<ClienteModelo> buscarPorAreaDistribuicaoId(int areaDistribuicaoId) {

        List<ClienteModelo> lista = new ArrayList<>();

        try {

            stream.seek(4);

            while (
                    stream.getFilePointer() < stream.length()) {

                ClienteModelo cliente = new ClienteModelo();

                cliente.read(stream);

                if (cliente.getAreaDistribuicaoId() == areaDistribuicaoId && cliente.isActivo()) {

                    lista.add(cliente);
                }
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return lista;
    }
        
    public List<ClienteModelo> buscarClientePorTipoContrato(TipoContratoEnum tipoContrato) {

        List<ClienteModelo> lista = new ArrayList<>();

        try {

            stream.seek(4);

            while (stream.getFilePointer() < stream.length()) {

                ClienteModelo cliente = new ClienteModelo();

                cliente.read(stream);
                if (cliente.getTipoContrato().trim().equals(tipoContrato.toString().trim()) && cliente.isActivo()) {
                    lista.add(cliente);
                }
            }

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return lista;
    }
    
}