package models;

import java.io.*;
import SwingComponents.*;
import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import modeloFiles.SolicitacaoFile;
import models.common.BaseModelo;
import models.common.ModeloUtil;
import provedores.UsuarioProvedor;

public class SolicitacaoModelo extends BaseModelo {
    
    private int clienteId;
    
    @CampoFormulario(
        descricao = "Tipo de Solicitação",
        largura = 200,
        obrigatorio = true,
        linha = 1
    )
    private StringBufferModelo tipoSolicitacao;
    
    @CampoFormulario(
        tipo = TipoCampo.MULTTEXTO,
        descricao = "Descrição",
        largura = 400,
        obrigatorio = true,
        linha = 2,
        altura = 60
    )
    private StringBufferModelo descricao;
    
    @CampoFormulario(
        descricao = "Prioridade",
        largura = 200,
        obrigatorio = true,
        linha = 3,
        tipo = TipoCampo.COMBO,
        opcoes = { "BAIXA", "ALTA", "URGENTE" }
    )
    private StringBufferModelo prioridade;
    
    @CampoFormulario(
        descricao = "Data de Abertura",
        largura = 200,
        obrigatorio = true,
        linha = 3
    )
    private DataModelo dataAbertura;
    
    @CampoFormulario(
        descricao = "Status",
        largura = 200,
        obrigatorio = true,
        linha = 4,
        tipo = TipoCampo.COMBO
    )
    private StringBufferModelo status;
    
    @CampoFormulario(
        descricao = "Técnico Responsável",
        largura = 200,
        obrigatorio = false,
        linha = 4,
        tipo = TipoCampo.COMBO,
        provider = UsuarioProvedor.class
    )
    private int tecnicoResponsavelId;
    
    public SolicitacaoModelo() {
        super();
        this.clienteId = 0;
        this.tipoSolicitacao = new StringBufferModelo(50);
        this.descricao = new StringBufferModelo(500);
        this.prioridade = new StringBufferModelo(20);
        this.dataAbertura = new DataModelo();
        this.status = new StringBufferModelo(30);
        this.tecnicoResponsavelId = 0;
    }
    
    public SolicitacaoModelo(int id, int clienteId, String tipoSolicitacao, 
                            String descricao, String prioridade, String dataAbertura,
                            String status, int tecnicoResponsavelId) {
        super();
        setId(id);
        this.clienteId = clienteId;
        this.tipoSolicitacao = new StringBufferModelo(tipoSolicitacao, 50);
        this.descricao = new StringBufferModelo(descricao, 500);
        this.prioridade = new StringBufferModelo(prioridade, 20);
        this.dataAbertura = new DataModelo(dataAbertura);
        this.status = new StringBufferModelo(status, 30);
        this.tecnicoResponsavelId = tecnicoResponsavelId;
    }
    
    public int getClienteId() {
        return clienteId;
    }
    
    public String getTipoSolicitacao() {
        return tipoSolicitacao.toStringEliminatingSpaces();
    }
    
    public String getDescricao() {
        return descricao.toStringEliminatingSpaces();
    }
    
    public String getPrioridade() {
        return prioridade.toStringEliminatingSpaces();
    }
    
    public String getDataAbertura() {
        return dataAbertura.toString();
    }
    
    public String getStatus() {
        return status.toStringEliminatingSpaces();
    }
    
    public int getTecnicoResponsavelId() {
        return tecnicoResponsavelId;
    }
    
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    
    public void setTipoSolicitacao(String tipoSolicitacao) {
        this.tipoSolicitacao = new StringBufferModelo(tipoSolicitacao, 50);
    }
    
    public void setDescricao(String descricao) {
        this.descricao = new StringBufferModelo(descricao, 500);
    }
    
    public void setPrioridade(String prioridade) {
        this.prioridade = new StringBufferModelo(prioridade, 20);
    }
    
    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = new DataModelo(dataAbertura);
    }
    
    public void setStatus(String status) {
        this.status = new StringBufferModelo(status, 30);
    }
    
    public void setTecnicoResponsavelId(int tecnicoResponsavelId) {
        this.tecnicoResponsavelId = tecnicoResponsavelId;
    }
    
    @Override
    public String toString() {
        String str = "Dados da Solicitação\n\n";
        
        str += "ID: " + getId() + "\n";
        str += "Cliente ID: " + getClienteId() + "\n";
        str += "Tipo de Solicitação: " + getTipoSolicitacao() + "\n";
        str += "Descrição: " + getDescricao() + "\n";
        str += "Prioridade: " + getPrioridade() + "\n";
        str += "Data de Abertura: " + getDataAbertura() + "\n";
        str += "Status: " + getStatus() + "\n";
        str += "Técnico Responsável ID: " + getTecnicoResponsavelId() + "\n";
        
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
            clienteId = stream.readInt();
            tipoSolicitacao.read(stream);
            descricao.read(stream);
            prioridade.read(stream);
            dataAbertura.read(stream);
            status.read(stream);
            tecnicoResponsavelId = stream.readInt();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void write(RandomAccessFile stream) {
        try {
            writeBase(stream);
            stream.writeInt(clienteId);
            tipoSolicitacao.write(stream);
            descricao.write(stream);
            prioridade.write(stream);
            dataAbertura.write(stream);
            status.write(stream);
            stream.writeInt(tecnicoResponsavelId);
        } catch(IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    public void salvarDados() {
        new SolicitacaoFile(this).salvarDados();
    }
    
    public void atualizarDados() {
        new SolicitacaoFile(this).atualizarDados(getId(), this);
    }
}