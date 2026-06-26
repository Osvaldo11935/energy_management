package modelos;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;

import SwingComponents.*;
import enums.EstadoCorteEnergiaEnum;
import modeloFiles.ClienteFile;
import modeloFiles.ContadorFile;
import modeloFiles.CorteEnergiaFile;
import modelos.common.BaseModelo;
import modelos.common.ModeloUtil;
import utils.DataMapper;

public class CorteEnergiaModelo extends BaseModelo {
    
    private int clienteId;
    private int areaId;
    private StringBufferModelo motivo;
    private DataModelo dataInicio;
    private DataModelo dataFim;
    private StringBufferModelo status;

    private ClienteModelo cliente;
    public CorteEnergiaModelo()
    {
       super();
       this.clienteId = 0;
       this.areaId = 0;
       motivo = new StringBufferModelo(100);
       dataInicio = new DataModelo();
       dataFim = new DataModelo();
       status = new StringBufferModelo(30);
    }

    public CorteEnergiaModelo(int id, int clienteId, String motivo)
    {
       super();
       setId(id);
       this.clienteId = clienteId;
       this.areaId = 0;
       this.motivo = new StringBufferModelo(motivo,100);
       this.dataInicio = new DataModelo(DataMapper.normalizarData(LocalDate.now().toString()));
       this.dataFim = new DataModelo(DataMapper.normalizarData(LocalDate.now().toString()));
       this.status = new StringBufferModelo(EstadoCorteEnergiaEnum.PENDENTE.toString(), 30);
    }
    public int getClienteId() {
        return clienteId;
    }
    public int getAreaId() {
        return areaId;
    }
    public String getMotivo() {
        return motivo.toStringEliminatingSpaces();
    }
    public String getDataInicio() {
        return dataInicio.toString();
    }
    public String getDataFim() {
        return dataFim.toString();
    }
    public String getStatus() {
        return status.toStringEliminatingSpaces();
    }
    public ClienteModelo getCliente()
    {
        return ClienteFile.instaciar().obterPorId(getClienteId());
    }
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public void setMotivo(String motivo) {
        this.motivo = new StringBufferModelo(motivo, 100);
    }
    public void setDataInicio(String dataInicio) {
        this.dataInicio =  new DataModelo(DataMapper.normalizarData(dataInicio));
    }
    public void setDataFim(String dataFim) {
        this.dataFim = new DataModelo(DataMapper.normalizarData(dataFim));
    }

    public void setStatus(String status) {
        this.status = new StringBufferModelo(status, 30);
    }
    
    @Override
    public String toString()
    {
        String str = "Dados do Corte de Energia\n\n";

        str += "ID: "+ getId() + "\n";
        str += "ClienteID: "+ getClienteId() + "\n";
        str += "AreaID: "+ getAreaId() + "\n";
        str += "Motivo: "+ getMotivo() + "\n";
        str += "Data Inicio: "+ getDataInicio() + "\n";
        str += "Data Fim: "+ getDataFim() + "\n";
        str += "Estado: "+ getStatus() + "\n";
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
            clienteId = stream.readInt();
            areaId = stream.readInt();
            motivo.read(stream);
            dataInicio.read(stream);
            dataFim.read(stream);
            status.read(stream);
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
            stream.writeInt(clienteId);
            stream.writeInt(areaId);
            motivo.write(stream);
            dataInicio.write(stream);
            dataFim.write(stream);
            status.write(stream);
        }
        catch(IOException ex)
		{
			ex.printStackTrace();
            throw new RuntimeException(ex);
		}
    }

    public void salvarDados()
    {
       new CorteEnergiaFile(this).salvarDados();
    }

    public void atualizarDados()
    {
       new CorteEnergiaFile(this).atualizarDados(getId(), this); 
    }
}
