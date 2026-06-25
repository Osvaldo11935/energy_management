package models;

import java.io.*;
import java.time.LocalDate;

import SwingComponents.*;
import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import modeloFiles.LeituraConsumoFile;
import models.common.BaseModelo;
import models.common.ModeloUtil;
import provedores.UsuarioProvedor;
import utils.DataMapper;


public class LeituraConsumoModelo extends BaseModelo {

    private int contadorId;

    @CampoFormulario(
        descricao = "Leitura Anterior",
        largura = 200,
        obrigatorio = true,
        linha = 1
    )
    private double leituraAnterior;

    @CampoFormulario(
        descricao = "Leitura Actual",
        largura = 200,
        obrigatorio = true,
        linha = 2
    )
    private double leituraActual;

    @CampoFormulario(
        descricao = "Consumo (kWh)",
        largura = 200,
        obrigatorio = true,
        linha = 2
    )
    private double consumoKwh;

    @CampoFormulario(
        descricao = "Período Início",
        largura = 200,
        obrigatorio = true,
        linha = 3
    )
    private DataModelo periodoInicio;

    @CampoFormulario(
        descricao = "Período Fim",
        largura = 200,
        obrigatorio = true,
        linha = 3
    )
    private DataModelo periodoFim;

    @CampoFormulario(
        descricao = "Data da Leitura",
        largura = 200,
        obrigatorio = true,
        linha = 4
    )
    private DataModelo dataLeitura;

    @CampoFormulario(
        descricao = "Responsável pela Leitura",
        largura = 200,
        obrigatorio = true,
        linha = 4,
        tipo = TipoCampo.COMBO,
        provider = UsuarioProvedor.class
    )
    private int responsavelLeituraId;

    public LeituraConsumoModelo() {
        super();
        this.contadorId = 0;
        this.leituraAnterior = 0.0;
        this.leituraActual = 0.0;
        this.consumoKwh = 0.0;
        this.periodoInicio = new DataModelo();
        this.periodoFim = new DataModelo();
        this.dataLeitura = new DataModelo();
        this.responsavelLeituraId = 0;
    }

    public LeituraConsumoModelo(int id, int contadorId, double leituraAnterior, double leituraActual, String periodoInicio, String periodoFim,  int responsavelLeituraId) {
        super();
        setId(id);
        this.contadorId = contadorId;
        this.leituraAnterior = leituraAnterior;
        this.leituraActual = leituraActual;
        this.consumoKwh = leituraAnterior - leituraActual;
        this.periodoInicio = new DataModelo(DataMapper.normalizarData(periodoInicio));
        this.periodoFim = new DataModelo(DataMapper.normalizarData(periodoFim));
        this.dataLeitura = new DataModelo(DataMapper.normalizarData(LocalDate.now().toString()));
        this.responsavelLeituraId = responsavelLeituraId;
    }

    public int getContadorId() {
        return contadorId;
    }

    public double getLeituraAnterior() {
        return leituraAnterior;
    }

    public double getLeituraActual() {
        return leituraActual;
    }

    public double getConsumoKwh() {
        return consumoKwh;
    }

    public String getPeriodoInicio() {
        return periodoInicio.toString();
    }

    public String getPeriodoFim() {
        return periodoFim.toString();
    }

    public String getDataLeitura() {
        return dataLeitura.toString();
    }

    public int getResponsavelLeituraId() {
        return responsavelLeituraId;
    }

    public void setContadorId(int contadorId) {
        this.contadorId = contadorId;
    }

    public void setLeituraAnterior(double leituraAnterior) {
        this.leituraAnterior = leituraAnterior;
    }

    public void setLeituraActual(double leituraActual) {
        this.leituraActual = leituraActual;
    }

    public void setConsumoKwh(double consumoKwh) {
        this.consumoKwh = consumoKwh;
    }

    public void setPeriodoInicio(String periodoInicio) {
        this.periodoInicio = new DataModelo(DataMapper.normalizarData(periodoInicio));
    }

    public void setPeriodoFim(String periodoFim) {
        this.periodoFim = new DataModelo(DataMapper.normalizarData(periodoFim));
    }

    public void setDataLeitura(String dataLeitura) {
        this.dataLeitura = new DataModelo(DataMapper.normalizarData(dataLeitura));
    }

    public void setResponsavelLeitura(int responsavelLeituraId) {
        this.responsavelLeituraId = responsavelLeituraId;
    }

    @Override
    public String toString() {
        String str = "Dados da Leitura de Consumo\n\n";
        
        str += "ID: " + getId() + "\n";
        str += "Contador ID: " + getContadorId() + "\n";
        str += "Leitura Anterior: " + getLeituraAnterior() + " kWh\n";
        str += "Leitura Actual: " + getLeituraActual() + " kWh\n";
        str += "Consumo: " + getConsumoKwh() + " kWh\n";
        str += "Período Início: " + getPeriodoInicio() + "\n";
        str += "Período Fim: " + getPeriodoFim() + "\n";
        str += "Data da Leitura: " + getDataLeitura() + "\n";
        str += "Responsável pela Leitura: " + getResponsavelLeituraId() + "\n";
        
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
            contadorId = stream.readInt();
            leituraAnterior = stream.readDouble();
            leituraActual = stream.readDouble();
            consumoKwh = stream.readDouble();
            periodoInicio.read(stream);
            periodoFim.read(stream);
            dataLeitura.read(stream);
            responsavelLeituraId = stream.readInt();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void write(RandomAccessFile stream) {
        try {
            writeBase(stream);
            stream.writeInt(contadorId);
            stream.writeDouble(leituraAnterior);
            stream.writeDouble(leituraActual);
            stream.writeDouble(consumoKwh);
            periodoInicio.write(stream);
            periodoFim.write(stream);
            dataLeitura.write(stream);
            stream.writeInt(responsavelLeituraId);
        } catch(IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void salvarDados() {
        new LeituraConsumoFile(this).salvarDados();
    }

    public void atualizarDados() {
        new LeituraConsumoFile(this).atualizarDados(getId(), this);
    }
}