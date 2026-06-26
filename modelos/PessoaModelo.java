package modelos;

import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import enums.*;
import modeloFiles.PessoaFile;
import modelos.common.BaseModelo;
import modelos.common.ModeloUtil;
import utils.DataMapper;
import SwingComponents.*;


import java.io.*;

public class PessoaModelo  extends BaseModelo {

    @CampoFormulario(
        descricao = "Nº BI", 
        obrigatorio = true, 
        largura = 150, 
        linha = 1
    )
    private StringBufferModelo numeroBI;

    @CampoFormulario(
        descricao = "Nome completo", 
        obrigatorio = true, 
        largura = 250, 
        linha = 1
    )
    private StringBufferModelo nomeCompleto;

    @CampoFormulario(
        descricao = "Nome do Pai", 
        obrigatorio = true, 
        largura = 200, 
        linha = 3
    )
    private StringBufferModelo nomePai;

    @CampoFormulario(
        descricao = "Nome da Mãe", 
        obrigatorio = true, 
        largura = 200, 
        linha = 3
    )
    private StringBufferModelo nomeMae;

    @CampoFormulario(
            descricao = "Data de Nascimento",
            obrigatorio = true,
            largura = 200,
            tipo = TipoCampo.DATA,
            linha = 4
    )
    private DataModelo dataNascimento;

    @CampoFormulario(
            descricao = "Estado civil",
            obrigatorio = true,
            tipo = TipoCampo.COMBO,
            enumType = EstadoCivilEnum.class,
            largura = 100,
            linha = 4
    )
    private StringBufferModelo estadoCivil;

    @CampoFormulario(
            descricao = "Género",
            obrigatorio = true,
            tipo = TipoCampo.COMBO,
            enumType = GeneroEnum.class,
            largura = 94,
            linha = 4
    )
    private StringBufferModelo genero;

    @CampoFormulario(
        descricao = "Residência", 
        obrigatorio = true, 
        largura = 200, 
        linha = 5
    )
    private StringBufferModelo residencia;

    @CampoFormulario(
        descricao = "Naturalidade", 
        obrigatorio = true, 
        largura = 200, 
        linha = 5
    )
    private StringBufferModelo naturalidade;

    @CampoFormulario(
        descricao = "Data da Emissão", 
        obrigatorio = true, 
        largura = 150,
        tipo =  TipoCampo.DATA,
        linha = 6
    )
    private DataModelo documentoEmitidoEm;

    @CampoFormulario(
        descricao = "Data de Validade", 
        obrigatorio = true, 
        largura = 150, 
        tipo =  TipoCampo.DATA,
        linha = 6
    )
    private DataModelo documentoValidoAte;

    @CampoFormulario(
        descricao = "Altura", 
        obrigatorio = true, 
        largura = 94, 
        linha = 6
    )
    private StringBufferModelo altura;

    private int usuarioId;

    public PessoaModelo() {
        super();
        this.numeroBI = new StringBufferModelo(15);
        this.nomeCompleto = new StringBufferModelo(50);
        this.nomePai = new StringBufferModelo(50);
        this.nomeMae = new StringBufferModelo(50);
        this.dataNascimento = new DataModelo();
        this.estadoCivil = new StringBufferModelo(10);
        this.genero = new StringBufferModelo(10);
        this.residencia = new StringBufferModelo(100);
        this.naturalidade = new StringBufferModelo(50);
        this.documentoEmitidoEm = new DataModelo();
        this.documentoValidoAte = new DataModelo();
        this.altura = new StringBufferModelo(5);
        this.usuarioId = 0;
    }

    public PessoaModelo(int id, String numeroBI, String nomeCompleto, String nomePai, String nomeMae, 
                  String dataNascimento, String estadoCivil, String genero, String residencia, 
                  String naturalidade, String documentoEmitidoEm, String documentoValidoAte, 
                  String altura, int usuarioId) {
        super();
        setId(id);
        this.numeroBI = new StringBufferModelo(numeroBI, 15);
        this.nomeCompleto = new StringBufferModelo(nomeCompleto, 50);
        this.nomePai = new StringBufferModelo(nomePai, 50);
        this.nomeMae = new StringBufferModelo(nomeMae, 50);
        this.dataNascimento = new DataModelo(DataMapper.normalizarData(dataNascimento));
        this.estadoCivil = new StringBufferModelo(estadoCivil, 10);
        this.genero = new StringBufferModelo(genero, 10);
        this.residencia = new StringBufferModelo(residencia, 100);
        this.naturalidade = new StringBufferModelo(naturalidade, 50);
        this.documentoEmitidoEm = new DataModelo(DataMapper.normalizarData(documentoEmitidoEm));
        this.documentoValidoAte = new DataModelo(DataMapper.normalizarData(documentoValidoAte));
        this.altura = new StringBufferModelo(altura, 5);
        this.usuarioId = usuarioId;
    }

    public String getNumeroBI() {
        return numeroBI.toStringEliminatingSpaces();
    }

    public String getNomeCompleto() {
        return nomeCompleto.toStringEliminatingSpaces();
    }

    public String getNomePai() {
        return nomePai.toStringEliminatingSpaces();
    }

    public String getNomeMae() {
        return nomeMae.toStringEliminatingSpaces();
    }

    public String getDataNascimento() {
        return dataNascimento.toString();
    }

    public String getEstadoCivil() {
        return estadoCivil.toStringEliminatingSpaces();
    }

    public String getGenero() {
        return genero.toStringEliminatingSpaces();
    }

    public String getResidencia() {
        return residencia.toStringEliminatingSpaces();
    }

    public String getNaturalidade() {
        return naturalidade.toStringEliminatingSpaces();
    }

    public String getDocumentoEmitidoEm() {
        return documentoEmitidoEm.toString();
    }

    public String getDocumentoValidoAte() {
        return documentoValidoAte.toString();
    }

    public String getAltura() {
        return altura.toStringEliminatingSpaces();
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setNumeroBI(String numeroBI) {
        this.numeroBI = new StringBufferModelo(numeroBI, 15);
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = new StringBufferModelo(nomeCompleto, 50);
    }

    public void setNomePai(String nomePai) {
        this.nomePai = new StringBufferModelo(nomePai, 50);
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = new StringBufferModelo(nomeMae, 50);
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = new DataModelo(DataMapper.normalizarData(dataNascimento));
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = new StringBufferModelo(estadoCivil, 10);
    }

    public void setGenero(String genero) {
        this.genero = new StringBufferModelo(genero, 10);
    }

    public void setResidencia(String residencia) {
        this.residencia = new StringBufferModelo(residencia, 100);
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = new StringBufferModelo(naturalidade, 50);
    }

    public void setDocumentoEmitidoEm(String documentoEmitidoEm) {
        this.documentoEmitidoEm = new DataModelo(DataMapper.normalizarData(documentoEmitidoEm));
    }

    public void setDocumentoValidoAte(String documentoValidoAte) {
        this.documentoValidoAte = new DataModelo(DataMapper.normalizarData(documentoValidoAte));
    }

    public void setAltura(String altura) {
        this.altura = new StringBufferModelo(altura, 5);
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String toString() {
        String str = "Dados da Pessoa\n\n";
        str += "ID: " + getId() + "\n";
        str += "Nº BI: " + getNumeroBI() + "\n";
        str += "Nome Completo: " + getNomeCompleto() + "\n";
        str += "Nome do Pai: " + getNomePai() + "\n";
        str += "Nome da Mãe: " + getNomeMae() + "\n";
        str += "Data de Nascimento: " + getDataNascimento() + "\n";
        str += "Estado Civil: " + getEstadoCivil() + "\n";
        str += "Gênero: " + getGenero() + "\n";
        str += "Residência: " + getResidencia() + "\n";
        str += "Naturalidade: " + getNaturalidade() + "\n";
        str += "Documento Emitido Em: " + getDocumentoEmitidoEm() + "\n";
        str += "Documento Válido Até: " + getDocumentoValidoAte() + "\n";
        str += "Altura: " + getAltura() + " m\n";
        str += "ID do Usuário: " + getUsuarioId() + "\n";
        return str;
    }
    
    public long sizeof()
    {
        return ModeloUtil.sizeOf(this);
    }
    
    public void read(RandomAccessFile stream)
    {
        try
        {
            readBase(stream);
            numeroBI.read(stream);
            nomeCompleto.read(stream);
            nomePai.read(stream);
            nomeMae.read(stream);
            dataNascimento.read(stream);
            estadoCivil.read(stream);
            genero.read(stream);
            residencia.read(stream);
            naturalidade.read(stream);
            documentoEmitidoEm.read(stream);
            documentoValidoAte.read(stream);
            altura.read(stream);
            usuarioId = stream.readInt();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void write(RandomAccessFile stream)
    {
        try
        {
            writeBase(stream);
            numeroBI.write(stream);
            nomeCompleto.write(stream);
            nomePai.write(stream);
            nomeMae.write(stream);
            dataNascimento.write(stream);
            estadoCivil.write(stream);
            genero.write(stream);
            residencia.write(stream);
            naturalidade.write(stream);
            documentoEmitidoEm.write(stream);
            documentoValidoAte.write(stream);
            altura.write(stream);
            stream.writeInt(usuarioId);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public void salvarDados()
    {
        new PessoaFile(this).salvarDados();
    }

}
