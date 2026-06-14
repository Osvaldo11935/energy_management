package models;

import java.io.IOException;
import java.io.RandomAccessFile;

import SwingComponents.*;
import modeloFiles.CorteEnergiaFile;
import modeloFiles.FaturaFile;
import models.common.BaseModelo;
import models.common.ModeloUtil;

public class FaturaModelo  extends BaseModelo{
    private int clienteId;

    private int contadorId;

    private int leituraId;

    private int tarifaId;

    private double consumoKwh;

    private double valorTotal;

    private double valorMulta;
 
    private double valorActualizado;

    private DataModelo dataEmissao;

    private DataModelo dataVencimento;

    private DataModelo dataPagamento;
 
    private StringBufferModelo status;

    private int diasAtraso;
    
    public FaturaModelo()
    {
        super();
        this.clienteId = 0;
        this.contadorId = 0;
        this.leituraId = 0;
        this.tarifaId = 0;
        this.consumoKwh = 0.0;
        this.valorTotal = 0.0;
        this.valorMulta = 0.0;
        this.valorActualizado = 0.0;
        this.dataEmissao = new DataModelo();
        this.dataVencimento = new DataModelo();
        this.dataPagamento = new DataModelo();
        this.status = new StringBufferModelo( 20);
        this.diasAtraso = 0;
    }
    public FaturaModelo(int id, int clienteId, int contadorId, int leituraId, int tarifaId, double consumoKwh, 
        double valorTotal, double valorMulta, double valorActualizado, DataModelo dataEmissao, DataModelo dataVencimento,DataModelo dataPagamento, String status, int diasAtraso) {
        super();
        setId(id);
        this.clienteId = clienteId;
        this.contadorId = contadorId;
        this.leituraId = leituraId;
        this.tarifaId = tarifaId;
        this.consumoKwh = consumoKwh;
        this.valorTotal = valorTotal;
        this.valorMulta = valorMulta;
        this.valorActualizado = valorActualizado;
        this.dataEmissao = dataEmissao;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.status = new StringBufferModelo(status, 20);
        this.diasAtraso = diasAtraso;
    }
    
    public int getClienteId() {
        return clienteId;
    }
    public int getContadorId() {
        return contadorId;
    }
    public int getLeituraId() {
        return leituraId;
    }
    public int getTarifaId() {
        return tarifaId;
    }
    public double getConsumoKwh() {
        return consumoKwh;
    }
    public double getValorTotal() {
        return valorTotal;
    }
    public double getValorMulta() {
        return valorMulta;
    }
    public double getValorActualizado() {
        return valorActualizado;
    }
    public String getDataEmissao() {
        return dataEmissao.toString();
    }
    public String getDataVencimento() {
        return dataVencimento.toString();
    }
    public String getDataPagamento() {
        return dataPagamento.toString();
    }
    public String getStatus() {
        return status.toStringEliminatingSpaces();
    }
    public int getDiasAtraso() {
        return diasAtraso;
    }
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    
    public void setContadorId(int contadorId) {
        this.contadorId = contadorId;
    }

    public void setLeituraId(int leituraId) {
        this.leituraId = leituraId;
    }

    public void setTarifaId(int tarifaId) {
        this.tarifaId = tarifaId;
    }

    public void setConsumoKwh(double consumoKwh) {
        this.consumoKwh = consumoKwh;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setValorMulta(double valorMulta) {
        this.valorMulta = valorMulta;
    }

    public void setValorActualizado(double valorActualizado) {
        this.valorActualizado = valorActualizado;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = new DataModelo(dataEmissao);
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = new DataModelo(dataVencimento);
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = new DataModelo(dataPagamento);
    }

    public void setStatus(String status) {
        this.status = new StringBufferModelo(status, 20);
    }

    public void setDiasAtraso(int diasAtraso) {
        this.diasAtraso = diasAtraso;
    }
    
    @Override
    public String toString()
    {
        String str = "Dados do Corte de Energia\n\n";

        str += "ID: "+ getId() + "\n";
        str += "ClienteID: "+ getClienteId() + "\n";
        str += "ContadorID: "+ getContadorId() + "\n";
        str += "LeituraID: "+ getLeituraId() + "\n";
        str += "TarifaID: "+ getTarifaId() + "\n";
        str += "Consumo(Kwh) "+ getConsumoKwh() + "\n";
        str += "Valor Total: "+ getValorTotal() + "\n";
        str += "Valor Multa: "+ getValorMulta() + "\n";
        str += "Valor Atualizado: "+ getValorActualizado() + "\n";
        str += "Data de Emissão: "+ getDataEmissao() + "\n";
        str += "Data de Vencimento: "+ getDataVencimento() + "\n";
        str += "Data de Pagamento: "+ getDataPagamento() + "\n";
        str += "Estado: "+ getStatus() + "\n";
        str += "Dia em Atraso: "+ getDiasAtraso() + "\n";
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
            contadorId = stream.readInt();
            leituraId = stream.readInt();
            tarifaId = stream.readInt();
            consumoKwh = stream.readDouble();
            valorTotal = stream.readDouble();
            valorMulta = stream.readDouble();
            valorActualizado = stream.readDouble();
            dataEmissao.read(stream);;
            dataVencimento.read(stream);;
            dataPagamento.read(stream);;
            status.read(stream); 
            diasAtraso = stream.readInt();
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
            stream.writeInt(contadorId);
            stream.writeInt(leituraId);
            stream.writeInt(tarifaId);
            stream.writeDouble(consumoKwh);
            stream.writeDouble(valorTotal);
            stream.writeDouble(valorMulta);
            stream.writeDouble(valorActualizado);
            dataEmissao.write(stream);
            dataVencimento.write(stream);
            dataPagamento.write(stream);
            status.write(stream); 
            stream.writeInt(diasAtraso);
        }
        catch(IOException ex)
		{
			ex.printStackTrace();
            throw new RuntimeException(ex);
		}
    }

    public void salvarDados()
    {
       new FaturaFile(this).salvarDados();
    }

    public void atualizarDados()
    {
       new FaturaFile(this).atualizarDados(getId(), this); 
    }
}
