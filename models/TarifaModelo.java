package models;

import java.io.*;
import SwingComponents.*;
import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import modeloFiles.TarifaFile;
import models.common.BaseModelo;
import models.common.ModeloUtil;

public class TarifaModelo extends BaseModelo {
    
    @CampoFormulario(
        descricao = "Nome da Tarifa",
        largura = 200,
        obrigatorio = true,
        linha = 1
    )
    private StringBufferModelo nomeTarifa;
    
    @CampoFormulario(
        descricao = "Preço por kWh",
        largura = 200,
        obrigatorio = true,
        linha = 2
    )
    private double precoKwh;
    
    @CampoFormulario(
        descricao = "Taxa Fixa",
        largura = 200,
        obrigatorio = true,
        linha = 2
    )
    private double taxaFixa;
    
    @CampoFormulario(
        descricao = "Multa por Atraso",
        largura = 200,
        obrigatorio = true,
        linha = 3
    )
    private double multaAtraso;
    
    @CampoFormulario(
        descricao = "Data de Vigor",
        largura = 200,
        obrigatorio = true,
        linha = 3
    )
    private DataModelo dataVigor;
    
    public TarifaModelo() {
        super();
        this.nomeTarifa = new StringBufferModelo(50);
        this.precoKwh = 0.0;
        this.taxaFixa = 0.0;
        this.multaAtraso = 0.0;
        this.dataVigor = new DataModelo();
    }
    
    public TarifaModelo(int id, String nomeTarifa, double precoKwh, 
                        double taxaFixa, double multaAtraso, String dataVigor) {
        super();
        setId(id);
        this.nomeTarifa = new StringBufferModelo(nomeTarifa, 50);
        this.precoKwh = precoKwh;
        this.taxaFixa = taxaFixa;
        this.multaAtraso = multaAtraso;
        this.dataVigor = new DataModelo(dataVigor);
    }
    
    public String getNomeTarifa() {
        return nomeTarifa.toStringEliminatingSpaces();
    }
    
    public double getPrecoKwh() {
        return precoKwh;
    }
    
    public double getTaxaFixa() {
        return taxaFixa;
    }
    
    public double getMultaAtraso() {
        return multaAtraso;
    }
    
    public String getDataVigor() {
        return dataVigor.toString();
    }
    
    public void setNomeTarifa(String nomeTarifa) {
        this.nomeTarifa = new StringBufferModelo(nomeTarifa, 50);
    }
    
    public void setPrecoKwh(double precoKwh) {
        this.precoKwh = precoKwh;
    }
    
    public void setTaxaFixa(double taxaFixa) {
        this.taxaFixa = taxaFixa;
    }
    
    public void setMultaAtraso(double multaAtraso) {
        this.multaAtraso = multaAtraso;
    }
    
    public void setDataVigor(String dataVigor) {
        this.dataVigor = new DataModelo(dataVigor);
    }
    
    @Override
    public String toString() {
        String str = "Dados da Tarifa\n\n";
        
        str += "ID: " + getId() + "\n";
        str += "Nome da Tarifa: " + getNomeTarifa() + "\n";
        str += "Preço por kWh: " + getPrecoKwh() + "\n";
        str += "Taxa Fixa: " + getTaxaFixa() + "\n";
        str += "Multa por Atraso: " + getMultaAtraso() + "\n";
        str += "Data de Vigor: " + getDataVigor() + "\n";
        
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
            nomeTarifa.read(stream);
            precoKwh = stream.readDouble();
            taxaFixa = stream.readDouble();
            multaAtraso = stream.readDouble();
            dataVigor.read(stream);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void write(RandomAccessFile stream) {
        try {
            writeBase(stream);
            nomeTarifa.write(stream);
            stream.writeDouble(precoKwh);
            stream.writeDouble(taxaFixa);
            stream.writeDouble(multaAtraso);
            dataVigor.write(stream);
        } catch(IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    public void salvarDados() {
        new TarifaFile(this).salvarDados();
    }
    
    public void atualizarDados() {
        new TarifaFile(this).atualizarDados(getId(), this);
    }
}