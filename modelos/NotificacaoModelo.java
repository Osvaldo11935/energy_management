package modelos;

import java.io.*;
import java.time.LocalDate;

import SwingComponents.*;
import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import enums.TipoNotificacaoEnum;
import modeloFiles.AreaDistribuicaoFile;
import modeloFiles.ClienteFile;
import modeloFiles.NotificacaoFile;
import modelos.common.BaseModelo;
import modelos.common.ModeloUtil;
import provedores.AreaDistribuicaoProvedor;
import provedores.ClienteProvedor;
import provedores.UsuarioProvedor;
import utils.DataMapper;

public class NotificacaoModelo extends BaseModelo {

    @CampoFormulario(
        descricao = "Cliente",
        largura = 200,
        obrigatorio = true,
        linha = 1,
        tipo = TipoCampo.COMBO,
        provider = ClienteProvedor.class
    )
    private int clienteId;

    @CampoFormulario(
        descricao = "Área",
        largura = 200,
        obrigatorio = true,
        linha = 1,
        tipo = TipoCampo.COMBO,
        provider = AreaDistribuicaoProvedor.class
    )
    private int areaId;

    @CampoFormulario(
        descricao = "Título",
        largura = 400,
        obrigatorio = true,
        linha = 2
    )
    private StringBufferModelo titulo;

    @CampoFormulario(
        tipo = TipoCampo.MULTTEXTO,
        descricao = "Mensagem",
        largura = 400,
        obrigatorio = true,
        linha = 3,
        altura = 80
    )
    private StringBufferModelo mensagem;

    @CampoFormulario(
        descricao = "Tipo",
        largura = 400,
        obrigatorio = true,
        linha = 4,
        enumType = TipoNotificacaoEnum.class,
        tipo = TipoCampo.COMBO
    )
    private StringBufferModelo tipo;

    private DataModelo dataEnvio;

    private boolean lida;
    
    private AreaDistribuicaoModelo areaDistribuicao;

    private ClienteModelo cliente;

    public NotificacaoModelo() {
        super();
        this.clienteId = 0;
        this.areaId = 0;
        this.titulo = new StringBufferModelo(100);
        this.mensagem = new StringBufferModelo(500);
        this.tipo = new StringBufferModelo(30);
        this.dataEnvio = new DataModelo();
        this.lida = false;
    }

    public NotificacaoModelo(int id, int clienteId, int areaId, String titulo, 
                            String mensagem, String tipo) {
        super();
        setId(id);
        this.clienteId = clienteId;
        this.areaId = areaId;
        this.titulo = new StringBufferModelo(titulo, 100);
        this.mensagem = new StringBufferModelo(mensagem, 500);
        this.tipo = new StringBufferModelo(tipo, 30);
        this.dataEnvio = new DataModelo(DataMapper.normalizarData(LocalDate.now().toString()));
        this.lida = false;
    }

    public int getClienteId() {
        return clienteId;
    }

    public int getAreaId() {
        return areaId;
    }

    public String getTitulo() {
        return titulo.toStringEliminatingSpaces();
    }

    public String getMensagem() {
        return mensagem.toStringEliminatingSpaces();
    }

    public String getTipo() {
        return tipo.toStringEliminatingSpaces();
    }

    public String getDataEnvio() {
        return dataEnvio.toString();
    }

    public boolean isLida() {
        return lida;
    }

    public boolean getLida() {
        return lida;
    }
    
    public ClienteModelo getCliente()
    {
        return ClienteFile.instaciar().obterPorId(getClienteId());
    }

    public AreaDistribuicaoModelo getAreaDistribuicao()
    {
        return AreaDistribuicaoFile.instaciar().obterPorId(getAreaId());
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public void setTitulo(String titulo) {
        this.titulo = new StringBufferModelo(titulo, 100);
    }

    public void setMensagem(String mensagem) {
        this.mensagem = new StringBufferModelo(mensagem, 500);
    }

    public void setTipo(String tipo) {
        this.tipo = new StringBufferModelo(tipo, 30);
    }

    public void setDataEnvio(String dataEnvio) {
        this.dataEnvio = new DataModelo(DataMapper.normalizarData(dataEnvio));
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    @Override
    public String toString() {
        String str = "Dados da Notificação\n\n";
        
        str += "ID: " + getId() + "\n";
        str += "Cliente ID: " + getClienteId() + "\n";
        str += "Área ID: " + getAreaId() + "\n";
        str += "Título: " + getTitulo() + "\n";
        str += "Mensagem: " + getMensagem() + "\n";
        str += "Tipo: " + getTipo() + "\n";
        str += "Data de Envio: " + getDataEnvio() + "\n";
        str += "Lida: " + (isLida() ? "Sim" : "Não") + "\n";
        
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
            areaId = stream.readInt();
            titulo.read(stream);
            mensagem.read(stream);
            tipo.read(stream);
            dataEnvio.read(stream);
            lida = stream.readBoolean();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void write(RandomAccessFile stream) {
        try {
            writeBase(stream);
            stream.writeInt(clienteId);
            stream.writeInt(areaId);
            titulo.write(stream);
            mensagem.write(stream);
            tipo.write(stream);
            dataEnvio.write(stream);
            stream.writeBoolean(lida);
        } catch(IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public void salvarDados() {
        new NotificacaoFile(this).salvarDados();
    }

    public void atualizarDados() {
        new NotificacaoFile(this).atualizarDados(getId(), this);
    }
}