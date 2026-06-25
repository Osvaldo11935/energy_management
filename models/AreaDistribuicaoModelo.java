package models;

import anotacoes.*;
import modeloFiles.*;
import models.common.*;
import java.io.*;
import provedores.*;
import SwingComponents.*;

public class AreaDistribuicaoModelo extends BaseModelo {
    @CampoFormulario(
        descricao = "Provincia", 
        tipo = TipoCampo.COMBO, 
        largura = 200,
        linha = 1,
        pesquisavel = true,
        provider = ProvinciaProvedor.class
    )
    private StringBufferModelo provincia;
    
    @CampoFormulario(
        descricao = "Municipio", 
        tipo = TipoCampo.COMBO, 
        largura = 200,
        linha = 1,
        pesquisavel = true,
        dependeDe = "provincia",
        provider = MunicipioProvedor.class
    )
    private StringBufferModelo municipio;
    
    @CampoFormulario(
        descricao = "Comuna",
        obrigatorio = true,
        largura = 200,
        linha = 2
    )
    private StringBufferModelo comuna;

    @CampoFormulario(
        descricao = "Bairro",
        obrigatorio = true,
        largura = 200,
        linha = 2
    )
    private StringBufferModelo bairro;

    @CampoFormulario(
        descricao = "Nº de Clientes",
        obrigatorio = true,
        largura = 200,
        linha = 3
    )
    private int numeroClientes;

    @CampoFormulario(
        descricao = "Subestação",
        obrigatorio = true,
        largura = 200,
        linha = 3,
        tipo = TipoCampo.COMBO,
        provider = SubestacaoProvedor.class
    )
    private int subestacaoId;

    private SubestacaoModelo subestacao;

    public AreaDistribuicaoModelo()
    {
        super();
        this.provincia = new StringBufferModelo(30);
        this.municipio = new StringBufferModelo( 30);
        this.comuna = new StringBufferModelo(30);
        this.bairro = new StringBufferModelo(30);
        this.numeroClientes = 0;
        this.subestacaoId = 0;

    }
    public AreaDistribuicaoModelo(int id, String provincia, String municipio, String comuna,
            String bairro, int numeroClientes, int subestacaoId) {
        super();
        setId(id);
        this.provincia = new StringBufferModelo(provincia, 30);
        this.municipio = new StringBufferModelo(municipio, 30);
        this.comuna = new StringBufferModelo(comuna, 30);
        this.bairro = new StringBufferModelo(bairro, 30);
        this.numeroClientes = numeroClientes;
        this.subestacaoId = subestacaoId;
    }

    public String getProvincia() {
        return provincia.toStringEliminatingSpaces();
    }
    public String getMunicipio() {
        return municipio.toStringEliminatingSpaces();
    }
    public String getComuna() {
        return comuna.toStringEliminatingSpaces();
    }
    public String getBairro() {
        return bairro.toStringEliminatingSpaces();
    }
    public int getNumeroClientes() {
        return numeroClientes;
    }
    public int getSubestacaoId() {
        return subestacaoId;
    }
    public SubestacaoModelo getSubestacao()
    {
        return new SubestacaoFile(new SubestacaoModelo()).obterPorId(getSubestacaoId());
    }
    public void setProvincia(String provincia) {
        this.provincia = new StringBufferModelo(provincia, 30);
    }
    public void setMunicipio(String municipio) {
        this.municipio = new StringBufferModelo(municipio, 30);
    }
    public void setComuna(String comuna) {
        this.comuna = new StringBufferModelo(comuna, 30);
    }

    public void setBairro(String bairro) {
        this.bairro = new StringBufferModelo(bairro, 30);
    }

    public void setNumeroClientes(int numeroClientes) {
        this.numeroClientes = numeroClientes;
    }

    public void setSubestacaoId(int subestacaoId) {
        this.subestacaoId = subestacaoId;
    }


    public String toString()
    {
        String str = "Dados do Perfil\n\n";

        str += "ID: "+ getId() + "\n";
        str += "Provincia: "+ getProvincia() + "\n";
        str += "Municipio: "+ getMunicipio() + "\n";
        str += "Comuna: "+ getComuna() + "\n";
        str += "Bairro: "+ getBairro() + "\n";
        str += "Numero de clientes: "+ getNumeroClientes() + "\n";
        str += "Subestacao: "+ getSubestacao().getNome() + "\n";

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
           provincia.read(stream);
           municipio.read(stream);
           comuna.read(stream);
           bairro.read(stream);
           numeroClientes = stream.readInt();
           subestacaoId = stream.readInt();
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
           provincia.write(stream);
           municipio.write(stream);
           comuna.write(stream);
           bairro.write(stream);
           stream.writeInt(numeroClientes);
           stream.writeInt(subestacaoId);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void salvarDados()
    {
        new AreaDistribuicaoFile(this).salvarDados();
    }
    public void atualizarDados()
    {
       new AreaDistribuicaoFile(this).atualizarDados(getId(), this); 
    }
}
