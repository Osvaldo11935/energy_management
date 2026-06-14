package models;

import java.io.*;
import SwingComponents.*;
import modeloFiles.ContadorFile;
import models.common.BaseModelo;
import models.common.ModeloUtil;

public class ContadorModelo extends BaseModelo {
    private StringBufferModelo numeroSerie;

    private StringBufferModelo tipoContador;

    private int clienteId;

    private int areaId;

    private DataModelo dataInstalacao;

    private double limiteConsumo;

    public ContadorModelo()
    {
        super();
        this.numeroSerie = new StringBufferModelo(30);
        this.tipoContador = new StringBufferModelo(30);
        this.clienteId = 0;
        this.areaId = 0;
        this.dataInstalacao = new DataModelo();
        this.limiteConsumo = 0.0;

    }
  
    public ContadorModelo(int id, String numeroSerie, String tipoContador, int clienteId, int areaId,
            String dataInstalacao, double limiteConsumo) {
        super();
        setId(id);
        this.numeroSerie = new StringBufferModelo(numeroSerie, 30);
        this.tipoContador = new StringBufferModelo(tipoContador, 30);
        this.clienteId = clienteId;
        this.areaId = areaId;
        this.dataInstalacao = new DataModelo(dataInstalacao);
        this.limiteConsumo = limiteConsumo;
    }

    public StringBufferModelo getNumeroSerie() {
        return numeroSerie;
    }
    public StringBufferModelo getTipoContador() {
        return tipoContador;
    }
    public int getClienteId() {
        return clienteId;
    }
    public int getAreaId() {
        return areaId;
    }
    public String getDataInstalacao() {
        return dataInstalacao.toString();
    }
    public double getLimiteConsumo() {
        return limiteConsumo;
    }
    public void setNumeroSerie(StringBufferModelo numeroSerie) {
        this.numeroSerie = numeroSerie;
    }
    public void setTipoContador(StringBufferModelo tipoContador) {
        this.tipoContador = tipoContador;
    }
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
    public void setDataInstalacao(String dataInstalacao) {
        this.dataInstalacao = new DataModelo(dataInstalacao);
    }
    public void setLimiteConsumo(double limiteConsumo) {
        this.limiteConsumo = limiteConsumo;
    }
    
    @Override
    public String toString()
    {
        String str = "Dados do cliente\n\n";

        str += "ID: "+ getId() + "\n";
        str += "Numero de Serie: "+ getNumeroSerie() + "\n";
        str += "Tipo de Contador: "+ getTipoContador() + "\n";
        str += "ClienteID: "+ getClienteId() + "\n";
        str += "AreaID: "+ getAreaId() + "\n";
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
            areaId = stream.readInt();
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
            stream.writeInt(areaId);
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
