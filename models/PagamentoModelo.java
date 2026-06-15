package models;

import java.io.*;
import SwingComponents.*;
import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import modeloFiles.PagamentoFile;
import models.common.BaseModelo;
import models.common.ModeloUtil;

public class PagamentoModelo extends BaseModelo {

    private int facturaId;

    private int clienteId;

    @CampoFormulario(
        descricao = "Valor Pago",
        largura = 200,
        obrigatorio = true,
        linha = 2
    )
    private double valorPago;

    @CampoFormulario(
        descricao = "Valor Multa Paga",
        largura = 200,
        obrigatorio = true,
        linha = 2
    )
    private double valorMultaPaga;
    private DataModelo dataPagamento;

    @CampoFormulario(
        descricao = "Método de Pagamento",
        largura = 200,
        obrigatorio = true,
        linha = 3,
        tipo = TipoCampo.COMBO,
        opcoes = { "MULTICAIXA", "DINHEIRO", "SISTEMA ENDE" }
    )
    private StringBufferModelo metodoPagamento;

    @CampoFormulario(
        descricao = "Referência da Transação",
        largura = 200,
        obrigatorio = true,
        linha = 4
    )
    private StringBufferModelo referenciaTransacao;

    @CampoFormulario(
        descricao = "Status",
        largura = 200,
        obrigatorio = true,
        linha = 4,
        tipo = TipoCampo.COMBO,
        opcoes = { "CANCELADO", "PAGO", "RECUSADO" }
    )
    private StringBufferModelo status;

    @CampoFormulario(
        descricao = "Comprovativo",
        largura = 300,
        obrigatorio = false,
        linha = 5
    )
    private StringBufferModelo comprovativoPath;

    public PagamentoModelo() {
        super();
        this.facturaId = 0;
        this.clienteId = 0;
        this.valorPago = 0.0;
        this.valorMultaPaga = 0.0;
        this.dataPagamento = new DataModelo();
        this.metodoPagamento = new StringBufferModelo(20);
        this.referenciaTransacao = new StringBufferModelo(15);
        this.status = new StringBufferModelo(15);
        this.comprovativoPath = new StringBufferModelo(30);
    }
    
    public PagamentoModelo(int id, int facturaId, int clienteId, double valorPago, 
                          double valorMultaPaga, String metodoPagamento, 
                          String referenciaTransacao, String status, String comprovativoPath) {
        super();
        setId(id);
        this.facturaId = facturaId;
        this.clienteId = clienteId;
        this.valorPago = valorPago;
        this.valorMultaPaga = valorMultaPaga;
        this.dataPagamento = new DataModelo();
        this.metodoPagamento = new StringBufferModelo(metodoPagamento, 20);
        this.referenciaTransacao = new StringBufferModelo(referenciaTransacao, 15);
        this.status = new StringBufferModelo(status, 15);
        this.comprovativoPath = new StringBufferModelo(comprovativoPath, 30);
    }

    public int getFacturaId() {
        return facturaId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public double getValorPago() {
        return valorPago;
    }

    public double getValorMultaPaga() {
        return valorMultaPaga;
    }

    public String getDataPagamento() {
        return dataPagamento.toString();
    }

    public String getMetodoPagamento() {
        return metodoPagamento.toStringEliminatingSpaces();
    }

    public String getReferenciaTransacao() {
        return referenciaTransacao.toStringEliminatingSpaces();
    }

    public String getStatus() {
        return status.toStringEliminatingSpaces();
    }

    public String getComprovativoPath() {
        return comprovativoPath.toStringEliminatingSpaces();
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public void setValorMultaPaga(double valorMultaPaga) {
        this.valorMultaPaga = valorMultaPaga;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = new DataModelo(dataPagamento);
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = new StringBufferModelo(metodoPagamento, 20);
    }

    public void setReferenciaTransacao(String referenciaTransacao) {
        this.referenciaTransacao = new StringBufferModelo(referenciaTransacao, 15);
    }

    public void setStatus(String status) {
        this.status = new StringBufferModelo(status, 15);
    }

    public void setComprovativoPath(String comprovativoPath) {
        this.comprovativoPath = new StringBufferModelo(comprovativoPath, 30);
    }

    @Override
    public String toString() {
        String str = "Dados do Pagamento\n\n";
        
        str += "ID: " + getId() + "\n";
        str += "Factura ID: " + getFacturaId() + "\n";
        str += "Cliente ID: " + getClienteId() + "\n";
        str += "Valor Pago: " + getValorPago() + "\n";
        str += "Valor Multa Paga: " + getValorMultaPaga() + "\n";
        str += "Data do Pagamento: " + getDataPagamento() + "\n";
        str += "Método de Pagamento: " + getMetodoPagamento() + "\n";
        str += "Referência da Transação: " + getReferenciaTransacao() + "\n";
        str += "Status: " + getStatus() + "\n";
        str += "Comprovativo Path: " + getComprovativoPath() + "\n";
        
        return str;
    }

    @Override
    public long sizeof() {
        return ModeloUtil.sizeOf(this);
    }

    @Override
    public void read(RandomAccessFile stream) {
        try {
            readBase(stream);
            facturaId = stream.readInt();
            clienteId = stream.readInt();
            valorPago = stream.readDouble();
            valorMultaPaga = stream.readDouble();
            dataPagamento.read(stream);
            metodoPagamento.read(stream);
            referenciaTransacao.read(stream);
            status.read(stream);
            comprovativoPath.read(stream);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void write(RandomAccessFile stream) {
        try {
            writeBase(stream);
            stream.writeInt(facturaId);
            stream.writeInt(clienteId);
            stream.writeDouble(valorPago);
            stream.writeDouble(valorMultaPaga);
            dataPagamento.write(stream);
            metodoPagamento.write(stream);
            referenciaTransacao.write(stream);
            status.write(stream);
            comprovativoPath.write(stream);
        } catch(IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void salvarDados() {
        new PagamentoFile(this).salvarDados();
    }

    public void atualizarDados() {
        new PagamentoFile(this).atualizarDados(getId(), this);
    }
}