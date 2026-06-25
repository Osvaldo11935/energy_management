package models;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;

import SwingComponents.*;
import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import modeloFiles.FaixaConsumoFile;
import modeloFiles.TarifaFile;
import models.common.BaseModelo;
import models.common.ModeloUtil;
import provedores.BoleanoProvedor;
import utils.DataMapper;

public class FaixaConsumoModelo extends BaseModelo {
    
    @CampoFormulario(descricao = "Limite Maximo", largura = 133, linha = 1)
    private double limiteMaximo;
    @CampoFormulario(descricao = "Limite Minimo", largura = 133, linha = 1)
    private double limiteMinimo;
    @CampoFormulario(descricao = "Preço", largura = 130, linha = 1)
    private double preco;
    @CampoFormulario(descricao = "Nome", largura = 400, linha = 2)
    private StringBufferModelo nome;
    @CampoFormulario(
        descricao = "Descrição", 
        tipo = TipoCampo.MULTTEXTO , 
        largura = 400, 
        altura = 80,
        linha = 3
    )
    private StringBufferModelo descricao;
    @CampoFormulario(
        descricao = "Ordem", 
        largura = 133, 
        linha = 4
    )
    private int ordem;
    private DataModelo dataCriacao;
    private DataModelo dataActualizacao;
    @CampoFormulario(
        descricao = "Desconto", 
        largura = 133, 
        linha = 4
    )
    private double desconto;
    @CampoFormulario(
        descricao = "Social", 
        tipo = TipoCampo.COMBO,
        largura = 130, 
        linha = 4,
        provider = BoleanoProvedor.class
    )
    private boolean social;
    @CampoFormulario(
        descricao = "Observações", 
        tipo = TipoCampo.MULTTEXTO, 
        largura = 400, 
        altura = 80,
        linha = 5
    )
    private StringBufferModelo observacoes; 
    
    private int tarifaId;
    private TarifaModelo tarifa;

    public FaixaConsumoModelo() {
        super();
        this.limiteMaximo = 0.0;
        this.preco = 0.0;
        this.limiteMinimo = 0.0;
        this.nome = new StringBufferModelo(30);
        this.descricao = new StringBufferModelo(100);
        this.ordem = 0;
        this.dataCriacao = new DataModelo(DataMapper.normalizarData(LocalDate.now().toString()));
        this.dataActualizacao = new DataModelo(DataMapper.normalizarData(LocalDate.now().toString()));
        this.desconto = 0.0;
        this.social = false;
        this.observacoes = new StringBufferModelo(200);
        this.tarifaId = 0;
    }
    
    public FaixaConsumoModelo(int id, double limiteMaximo, double preco, double limiteMinimo, 
                              String nome, String descricao, int ordem,double desconto, 
                              boolean social, String observacoes, int tarifaId) {
        super();
        setId(id);
        this.limiteMaximo = limiteMaximo;
        this.preco = preco;
        this.limiteMinimo = limiteMinimo;
        this.nome = new StringBufferModelo(nome, 30);
        this.descricao = new StringBufferModelo(descricao, 100);
        this.ordem = ordem;
        this.dataCriacao = new DataModelo(DataMapper.normalizarData(LocalDate.now().toString()));
        this.dataActualizacao = new DataModelo(DataMapper.normalizarData(LocalDate.now().toString()));
        this.desconto = desconto;
        this.social = social;
        this.observacoes = new StringBufferModelo(observacoes, 200);
        this.tarifaId = tarifaId;
    }
    
    public double getLimiteMaximo() {
        return limiteMaximo;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public double getLimiteMinimo() {
        return limiteMinimo;
    }
    
    public String getNome() {
        return nome.toStringEliminatingSpaces();
    }
    
    public String getDescricao() {
        return descricao.toStringEliminatingSpaces();
    }
    
    public int getOrdem() {
        return ordem;
    }
    
    public String getDataCriacao() {
        return dataCriacao.toString();
    }
    
    public String getDataActualizacao() {
        return dataActualizacao.toString();
    }
    
    public double getDesconto() {
        return desconto;
    }
    
    public boolean isSocial() {
        return social;
    }
    
    public String getObservacoes() {
        return observacoes.toStringEliminatingSpaces();
    }

    public int getTarifaId() {
        return tarifaId;
    }

    public TarifaModelo getTarifa()
    {
        return TarifaFile.instaciar().obterPorId(getTarifaId());
    }

    public void setLimiteMaximo(double limiteMaximo) {
        this.limiteMaximo = limiteMaximo;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public void setLimiteMinimo(double limiteMinimo) {
        this.limiteMinimo = limiteMinimo;
    }
    
    public void setNome(String nome) {
        this.nome = new StringBufferModelo(nome, 30);
    }
    
    public void setDescricao(String descricao) {
        this.descricao = new StringBufferModelo(descricao, 100);
    }
    
    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }
    
    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = new DataModelo(dataCriacao);
    }
    
    public void setDataActualizacao(String dataActualizacao) {
        this.dataActualizacao = new DataModelo(dataActualizacao);
    }
    
    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }
    
    public void setSocial(boolean social) {
        this.social = social;
    }
    
    public void setObservacoes(String observacoes) {
        this.observacoes = new StringBufferModelo(observacoes, 200);
    }
    
    public double calcularValor(double consumo) {
        if (consumo <= 0) return 0.0;
        
        double consumoNaFaixa = Math.min(consumo, limiteMaximo - limiteMinimo + 1);
        double valor = consumoNaFaixa * preco;

        if (desconto > 0) {
            valor = valor * (1 - desconto);
        }
        
        return valor;
    }
    
    public boolean isConsumoNaFaixa(double consumo) {
        return consumo >= limiteMinimo && consumo <= limiteMaximo;
    }
    
    public String getIntervaloString() {
        if (limiteMaximo >= 999999) {
            return "> " + (int)limiteMinimo + " kWh";
        }
        return (int)limiteMinimo + " - " + (int)limiteMaximo + " kWh";
    }
    
    public String getDescricaoCompleta() {
        return String.format("%s: %s → %.2f KZ/kWh", 
            getNome() != null && !getNome().isEmpty() ? getNome() : getIntervaloString(),
            getIntervaloString(),
            preco
        );
    }
    
    @Override
    public String toString() {
        String str = "Dados da Faixa de Consumo\n\n";
        
        str += "ID: " + getId() + "\n";
        str += "Nome: " + getNome() + "\n";
        str += "Descrição: " + getDescricao() + "\n";
        str += "Limite Mínimo: " + limiteMinimo + " kWh\n";
        str += "Limite Máximo: " + limiteMaximo + " kWh\n";
        str += "Preço: " + preco + " KZ/kWh\n";
        str += "Ordem: " + ordem + "\n";
        str += "Desconto: " + (desconto * 100) + "%\n";
        str += "Social: " + (social ? "Sim" : "Não") + "\n";
        str += "Data Criação: " + getDataCriacao() + "\n";
        str += "Data Atualização: " + getDataActualizacao() + "\n";
        str += "Observações: " + getObservacoes() + "\n";
        
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
            
            limiteMaximo = stream.readDouble();
            preco = stream.readDouble();
            limiteMinimo = stream.readDouble();
            nome.read(stream);
            descricao.read(stream);
            ordem = stream.readInt();
            dataCriacao.read(stream);
            dataActualizacao.read(stream);
            desconto = stream.readDouble();
            social = stream.readBoolean();
            observacoes.read(stream);
            tarifaId = stream.readInt();
            
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void write(RandomAccessFile stream) {
        try {
            writeBase(stream);
            
            stream.writeDouble(limiteMaximo);
            stream.writeDouble(preco);
            stream.writeDouble(limiteMinimo);
            nome.write(stream);
            descricao.write(stream);
            stream.writeInt(ordem);
            dataCriacao.write(stream);
            dataActualizacao.write(stream);
            stream.writeDouble(desconto);
            stream.writeBoolean(social);
            observacoes.write(stream);
            stream.writeInt(tarifaId);
            
        } catch(IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    public void salvarDados() {
        new FaixaConsumoFile(this).salvarDados();
    }
    
    public void atualizarDados() {
        new FaixaConsumoFile(this).atualizarDados(getId(), this);
    }
}