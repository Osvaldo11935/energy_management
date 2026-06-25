package models;

import java.io.*;
import java.time.LocalDate;
import java.util.Random;
import SwingComponents.*;
import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import enums.TipoContador;
import modeloFiles.ContadorFile;
import models.common.BaseModelo;
import models.common.ModeloUtil;
import utils.DataMapper;

public class ContadorModelo extends BaseModelo {
    private StringBufferModelo numeroSerie;
    @CampoFormulario(
        descricao = "Tipo de Contador",
        enumType = TipoContador.class,
        tipo = TipoCampo.COMBO,
        largura = 400,
        linha = 1
    )
    private StringBufferModelo tipoContador;

    private int clienteId;

    @CampoFormulario(
        descricao = "Data da Instalação",
        tipo = TipoCampo.DATA,
        largura = 400,
        linha = 2
    )
    private DataModelo dataInstalacao;

    private double limiteConsumo;

    public ContadorModelo()
    {
        super();
        this.numeroSerie = new StringBufferModelo(14);
        this.tipoContador = new StringBufferModelo(30);
        this.clienteId = 0;
        this.dataInstalacao = new DataModelo();
        this.limiteConsumo = 0.0;

    }
  
    public ContadorModelo(int id, String tipoContador, int clienteId,
            String dataInstalacao, double limiteConsumo) {
        super();
        setId(id);
        String nSerie = gerarNumeroSerie(10);
        this.numeroSerie = new StringBufferModelo(nSerie, 14);
        this.tipoContador = new StringBufferModelo(tipoContador, 30);
        this.clienteId = clienteId;
        this.dataInstalacao = new DataModelo(DataMapper.normalizarData(dataInstalacao));
        this.limiteConsumo = limiteConsumo;
    }

    public String getNumeroSerie() {
        return numeroSerie.toStringEliminatingSpaces();
    }
    public String getTipoContador() {
        return tipoContador.toStringEliminatingSpaces();
    }
    public int getClienteId() {
        return clienteId;
    }
    public String getDataInstalacao() {
        return dataInstalacao.toString();
    }
    public double getLimiteConsumo() {
        return limiteConsumo;
    }
    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = new StringBufferModelo(numeroSerie, 14);
    }
    public void setTipoContador(String tipoContador) {
        this.tipoContador = new StringBufferModelo(tipoContador, 30);
    }
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setDataInstalacao(String dataInstalacao) {
        this.dataInstalacao = new DataModelo(DataMapper.normalizarData(dataInstalacao));
    }
    public void setLimiteConsumo(double limiteConsumo) {
        this.limiteConsumo = limiteConsumo;
    }
    public static String gerarNumeroSerie(int tamanho) {

        if (tamanho <= 0) {
            throw new IllegalArgumentException("O tamanho deve ser maior que zero");
        }

        Random random = new Random();

        StringBuilder numeroSerie = new StringBuilder();

        for (int i = 0; i < tamanho; i++) {
            numeroSerie.append(random.nextInt(10));
        }

        return numeroSerie.toString();
    }
    @Override
    public String toString()
    {
        String str = "Dados do cliente\n\n";

        str += "ID: "+ getId() + "\n";
        str += "Numero de Serie: "+ getNumeroSerie() + "\n";
        str += "Tipo de Contador: "+ getTipoContador() + "\n";
        str += "ClienteID: "+ getClienteId() + "\n";
        str += "Data de Instalação: "+ getDataInstalacao() + "\n";
        str += "Limite de Consumo: "+ getLimiteConsumo() + "\n";
        return str;
    }

    @Override
    public  long sizeof()
    {
        return ModeloUtil.sizeOf(this);
    }

    @Override
    public void read(RandomAccessFile stream) 
    {
        try
        {
            readBase(stream);
            numeroSerie.read(stream);
            tipoContador.read(stream);
            clienteId = stream.readInt();
            dataInstalacao.read(stream);
            limiteConsumo = stream.readDouble();
        }
        catch(IOException ex)
		{
			ex.printStackTrace();
		}
    }
	
    @Override
    public void write(RandomAccessFile stream)
    {
        try
        {
            writeBase(stream);
            numeroSerie.write(stream);
            tipoContador.write(stream);
            stream.writeInt(clienteId);
            dataInstalacao.write(stream);
            stream.writeDouble(limiteConsumo);
        }
        catch(IOException ex)
		{
			ex.printStackTrace();
            throw new RuntimeException(ex);
		}
    }

    public void salvarDados()
    {
       new ContadorFile(this).salvarDados();
    }

    public void atualizarDados()
    {
       new ContadorFile(this).atualizarDados(getId(), this); 
    }
}
