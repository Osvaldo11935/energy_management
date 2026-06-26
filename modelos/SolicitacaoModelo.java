package modelos;

import java.io.*;
import java.time.LocalDate;

import SwingComponents.*;
import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import enums.EstadoSolicitacaoEnum;
import enums.PrioridadeEnum;
import enums.TipoSolicitacaoEnum;
import modeloFiles.ClienteFile;
import modeloFiles.SolicitacaoFile;
import modeloFiles.UsuarioFile;
import modelos.common.BaseModelo;
import modelos.common.ModeloUtil;
import provedores.ContratoProvedor;
import provedores.TecnicosProvedor;
import provedores.UsuarioProvedor;
import utils.DataMapper;

public class SolicitacaoModelo extends BaseModelo {
    
    private int usuarioId;
    @CampoFormulario(
        descricao = "Contrato do Cliente",
        largura = 200,
        obrigatorio = true,
        linha = 1,
        tipo = TipoCampo.COMBO,
        provider = ContratoProvedor.class
    )
    private int contratoId;
    @CampoFormulario(
        descricao = "Tipo de Solicitação",
        largura = 200,
        obrigatorio = true,
        linha = 1,
        tipo = TipoCampo.COMBO,
        enumType = TipoSolicitacaoEnum.class
    )
    private StringBufferModelo tipoSolicitacao;
    
    @CampoFormulario(
        tipo = TipoCampo.MULTTEXTO,
        descricao = "Descrição",
        largura = 400,
        obrigatorio = true,
        linha = 2,
        altura = 80
    )
    private StringBufferModelo descricao;
    
    @CampoFormulario(
        descricao = "Prioridade",
        largura = 200,
        obrigatorio = true,
        linha = 3,
        tipo = TipoCampo.COMBO,
        enumType = PrioridadeEnum.class
    )
    private StringBufferModelo prioridade;
    
    private DataModelo dataAbertura;
    
    private StringBufferModelo status;
    
    @CampoFormulario(
        descricao = "Técnico Responsável",
        largura = 200,
        obrigatorio = false,
        linha = 3,
        tipo = TipoCampo.COMBO,
        provider = TecnicosProvedor.class
    )
    private int tecnicoResponsavelId;

    private int solicitacaoPaiId;
    
    private ClienteModelo contrato;

    private UsuarioModelo tecnicoResponsavel;

    public SolicitacaoModelo() {
        super();
        this.usuarioId = 0;
        this.contratoId = 0;
        this.tipoSolicitacao = new StringBufferModelo(50);
        this.descricao = new StringBufferModelo(500);
        this.prioridade = new StringBufferModelo(20);
        this.dataAbertura = new DataModelo();
        this.status = new StringBufferModelo(30);
        this.tecnicoResponsavelId = 0;
    }
    
    public SolicitacaoModelo(int id, int usuarioId, int contratoId, String tipoSolicitacao, 
                            String descricao, String prioridade, int tecnicoResponsavelId, int solicitacaoPaiId) {
        super();
        setId(id);
        this.usuarioId = usuarioId;
        this.contratoId = contratoId;
        this.tipoSolicitacao = new StringBufferModelo(tipoSolicitacao, 50);
        this.descricao = new StringBufferModelo(descricao, 500);
        this.prioridade = new StringBufferModelo(prioridade, 20);
        this.dataAbertura = new DataModelo(DataMapper.normalizarData(LocalDate.now().toString()));
        this.status = new StringBufferModelo(EstadoSolicitacaoEnum.PENDENTE.toString(), 30);
        this.tecnicoResponsavelId = tecnicoResponsavelId;
        this.solicitacaoPaiId = solicitacaoPaiId;
    }
    public int getUsuarioId() {
        return usuarioId;
    }

    public int getContratoId() {
        return contratoId;
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
    public int getSolicitacaoPaiId()
    {
        return solicitacaoPaiId;
    }
    
    public ClienteModelo getContrato()
    {
       return ClienteFile.instaciar().obterPorId(getContratoId());
    }

    public UsuarioModelo getTecnicoResponsavel()
    {
        return UsuarioFile.instaciar().obterPorId(getTecnicoResponsavelId());
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setContratoId(int contratoId) {
        this.contratoId = contratoId;
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
    public void setSolicitacaoPaiId(int solicitacaoPaiId)
    {
        this.solicitacaoPaiId = solicitacaoPaiId;
    }
    @Override
    public String toString() {
        String str = "Dados da Solicitação\n\n";
        
        str += "ID: " + getId() + "\n";
        str += "Usuario ID: " + getUsuarioId() + "\n";
        str += "Contrato ID: " + getContratoId() + "\n";
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
            usuarioId = stream.readInt();
            contratoId = stream.readInt();
            tipoSolicitacao.read(stream);
            descricao.read(stream);
            prioridade.read(stream);
            dataAbertura.read(stream);
            status.read(stream);
            tecnicoResponsavelId = stream.readInt();
            solicitacaoPaiId = stream.readInt();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void write(RandomAccessFile stream) {
        try {
            writeBase(stream);
            stream.writeInt(usuarioId);
            stream.writeInt(contratoId);
            tipoSolicitacao.write(stream);
            descricao.write(stream);
            prioridade.write(stream);
            dataAbertura.write(stream);
            status.write(stream);
            stream.writeInt(tecnicoResponsavelId);
            stream.writeInt(solicitacaoPaiId);
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