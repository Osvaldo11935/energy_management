package modelos;

import java.io.*;
import java.time.LocalDate;

import SwingComponents.*;
import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import modeloFiles.MenuFile;
import modeloFiles.SubestacaoFile;
import modelos.common.BaseModelo;
import modelos.common.ModeloUtil;
import provedores.MunicipioProvedor;
import provedores.ProvinciaProvedor;
import provedores.UsuarioProvedor;
import utils.DataMapper;

public class SubestacaoModelo extends BaseModelo {
    @CampoFormulario(
        descricao = "Codigo",
        largura = 200,
        obrigatorio = true,
        linha = 1
    )
    private StringBufferModelo codigo;
    @CampoFormulario(
        descricao = "Nome",
        largura = 200,
        obrigatorio = true,
        linha = 1
    )
    private StringBufferModelo nome;

    @CampoFormulario(
        descricao = "Localização",
        largura = 400,
        obrigatorio = true,
        linha = 2
    )
    private StringBufferModelo localizacao;

    @CampoFormulario(
        descricao = "Provincia",
        largura = 200,
        obrigatorio = true,
        linha = 3,
        tipo = TipoCampo.COMBO,
        provider = ProvinciaProvedor.class
    )
    private StringBufferModelo provincia;
    @CampoFormulario(
        descricao = "Municipio",
        largura = 200,
        obrigatorio = true,
        linha = 3,
        tipo = TipoCampo.COMBO,
        provider = MunicipioProvedor.class
    )
    private StringBufferModelo municipio;
    @CampoFormulario(
        descricao = "Capacidade",
        largura = 133,
        obrigatorio = true,
        linha = 4
    )
    private double capacidade;
    @CampoFormulario(
        descricao = "Tensão Nominal",
        largura = 133,
        obrigatorio = true,
        linha = 4
    )
    private double tensaoNominal;
    private DataModelo dataInstalacao;
    private DataModelo ultimaManutencao;
    @CampoFormulario(
        descricao = "Responsavel",
        largura = 130,
        obrigatorio = true,
        linha = 4,
        tipo = TipoCampo.COMBO,
        provider = UsuarioProvedor.class
    )
    private int usuarioId;
    @CampoFormulario(
        descricao = "Latitude",
        largura = 200,
        obrigatorio = true,
        linha = 5
    )
    private double latitude;
    @CampoFormulario(
        descricao = "Longitude",
        largura = 200,
        obrigatorio = true,
        linha = 5
    )
    private double longitude;

    @CampoFormulario(
        descricao = "Observações",
        largura = 400,
        altura = 80,
        obrigatorio = true,
        linha = 6
    )
    private StringBufferModelo observacoes;
    public SubestacaoModelo()
    {
        super();
        this.codigo = new StringBufferModelo( 30);
        this.nome = new StringBufferModelo( 30);
        this.localizacao = new StringBufferModelo( 100);
        this.provincia = new StringBufferModelo( 30);
        this.municipio = new StringBufferModelo( 30);
        this.capacidade = 0.0;
        this.tensaoNominal = 0.0;
        this.dataInstalacao = new DataModelo();
        this.ultimaManutencao = new DataModelo();
        this.usuarioId = 0;
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.observacoes = new StringBufferModelo( 100);
    }
    public SubestacaoModelo(int id, String codigo, String nome, String localizacao,
            String provincia, String municipio, double capacidade, double tensaoNominal,
            int usuarioId, double latitude, double longitude,
            String observacoes) {
        super();
        setId(id);
        this.codigo = new StringBufferModelo(codigo, 30);
        this.nome = new StringBufferModelo(nome, 30);
        this.localizacao = new StringBufferModelo(localizacao, 100);
        this.provincia = new StringBufferModelo(provincia, 30);
        this.municipio = new StringBufferModelo(municipio, 30);
        this.capacidade = capacidade;
        this.tensaoNominal = tensaoNominal;
        this.dataInstalacao = new DataModelo(DataMapper.normalizarData(LocalDate.now().toString()));
        this.ultimaManutencao = new DataModelo(DataMapper.normalizarData(LocalDate.now().toString()));
        this.usuarioId = usuarioId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.observacoes = new StringBufferModelo(observacoes, 100);
    }

    public String getCodigo() {
        return codigo.toStringEliminatingSpaces();
    }

    public String getNome() {
        return nome.toStringEliminatingSpaces();
    }

    public String getLocalizacao() {
        return localizacao.toStringEliminatingSpaces();
    }

    public String getProvincia() {
        return provincia.toStringEliminatingSpaces();
    }

    public String getMunicipio() {
        return municipio.toStringEliminatingSpaces();
    }

    public double getCapacidade() {
        return capacidade;
    }

    public double getTensaoNominal() {
        return tensaoNominal;
    }

    public String getDataInstalacao() {
        return dataInstalacao.toString();
    }

    public String getUltimaManutencao() {
        return ultimaManutencao.toString();
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getObservacoes() {
        return observacoes.toStringEliminatingSpaces();
    }

    public void setCodigo(String codigo) {
        this.codigo = new StringBufferModelo(codigo, 30);
    }

    public void setNome(String nome) {
        this.nome = new StringBufferModelo(nome, 30);
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = new StringBufferModelo(localizacao, 100);
    }

    public void setProvincia(String provincia) {
        this.provincia = new StringBufferModelo(provincia, 30);
    }

    public void setMunicipio(String municipio) {
        this.municipio = new StringBufferModelo(municipio, 30);
    }

    public void setCapacidade(double capacidade) {
        this.capacidade = capacidade;
    }

    public void setTensaoNominal(double tensaoNominal) {
        this.tensaoNominal = tensaoNominal;
    }

    public void setDataInstalacao(String dataInstalacao) {
        this.dataInstalacao = new DataModelo(dataInstalacao);
    }

    public void setUltimaManutencao(String ultimaManutencao) {
        this.ultimaManutencao = new DataModelo(ultimaManutencao);
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = new StringBufferModelo(observacoes, 100);
    }
    @Override
    public String toString()
    {
        String str = "Dados do Corte de Energia\n\n";

        str += "ID: "+ getId() + "\n";
        str += "Codigo: "+ getCodigo() + "\n";
        str += "Nome: "+ getNome() + "\n";
        str += "Localização: "+ getLocalizacao() + "\n";
        str += "Provincia: "+ getProvincia() + "\n";
        str += "Municipio: "+ getMunicipio() + "\n";
        str += "Capacidade: "+ getCapacidade() + "\n";
        str += "Tensão Nominal: "+ getTensaoNominal() + "\n";
        str += "Data da Instalação: "+ getDataInstalacao() + "\n";
        str += "Ultima Manutenção: "+ getUltimaManutencao() + "\n";
        str += "UsuarioID: "+ getUsuarioId() + "\n";
        str += "Latitude: "+ getLatitude() + "\n";
        str += "Longitude: "+ getLongitude() + "\n";
        str += "Observações: "+ getObservacoes() + "\n";
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
            codigo.read(stream);
            nome.read(stream); 
            localizacao.read(stream); 
            provincia.read(stream);  
            municipio.read(stream);  
            capacidade = stream.readDouble(); 
            tensaoNominal = stream.readDouble();
            dataInstalacao.read(stream);
            ultimaManutencao.read(stream);
            usuarioId = stream.readInt();
            latitude = stream.readDouble(); 
            longitude = stream.readDouble(); 
            observacoes.read(stream);
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
            codigo.write(stream);
            nome.write(stream); 
            localizacao.write(stream); 
            provincia.write(stream);  
            municipio.write(stream);  
            stream.writeDouble(capacidade); 
            stream.writeDouble(tensaoNominal);
            dataInstalacao.write(stream);
            ultimaManutencao.write(stream);
            stream.writeInt(usuarioId);
            stream.writeDouble(latitude); 
            stream.writeDouble(longitude); 
            observacoes.write(stream);
        }
        catch(IOException ex)
		{
			ex.printStackTrace();
            throw new RuntimeException(ex);
		}
    }

    public void salvarDados()
    {
       new SubestacaoFile(this).salvarDados();
    }

    public void atualizarDados()
    {
       new SubestacaoFile(this).atualizarDados(getId(), this); 
    }
}