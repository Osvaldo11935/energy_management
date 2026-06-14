package models;

import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import modeloFiles.AreaDistribuicaoFile;
import modeloFiles.ClienteFile;
import models.common.BaseModelo;
import models.common.ModeloUtil;
import java.io.*;
import SwingComponents.*;
import provedores.AreaDistribuicaoProvedor;
import provedores.MunicipioProvedor;
import provedores.ProvinciaProvedor;

public class AreaDistribuicaoModelo extends BaseModelo {
    @CampoFormulario(
        descricao = "Provincia", 
        tipo = TipoCampo.COMBO, 
        largura = 200,
        linha = 1,
        provider = ProvinciaProvedor.class
    )
    private StringBufferModelo provincia;
    
    @CampoFormulario(
        descricao = "Municipio", 
        tipo = TipoCampo.COMBO, 
        largura = 200,
        linha = 1,
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
        descricao = "Codigo Postal",
        obrigatorio = true,
        largura = 200,
        linha = 3
    )
    private StringBufferModelo codigoPostal;

    @CampoFormulario(
        descricao = "Subestação",
        obrigatorio = true,
        largura = 200,
        linha = 3,
        tipo = TipoCampo.COMBO,
        provider = AreaDistribuicaoProvedor.class
    )
    private int subestacaoId;
    public AreaDistribuicaoModelo()
    {
        super();
        this.provincia = new StringBufferModelo(30);
        this.municipio = new StringBufferModelo( 30);
        this.comuna = new StringBufferModelo(30);
        this.bairro = new StringBufferModelo(30);
        this.codigoPostal = new StringBufferModelo(10);
        this.subestacaoId = 0;

    }
    public AreaDistribuicaoModelo(int id, String provincia, String municipio, String comuna,
            String bairro, String codigoPostal, int subestacaoId) {
        super();
        setId(id);
        this.provincia = new StringBufferModelo(provincia, 30);
        this.municipio = new StringBufferModelo(municipio, 30);
        this.comuna = new StringBufferModelo(comuna, 30);
        this.bairro = new StringBufferModelo(bairro, 30);
        this.codigoPostal = new StringBufferModelo(codigoPostal,10);
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
    public String getCodigoPostal() {
        return codigoPostal.toStringEliminatingSpaces();
    }
    public int getSubestacaoId() {
        return subestacaoId;
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

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = new StringBufferModelo(codigoPostal, 10);
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
        str += "Codigo Postal: "+ getCodigoPostal() + "\n";
        str += "Subestacao: "+ getSubestacaoId() + "\n";

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
           codigoPostal.read(stream);
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
           codigoPostal.write(stream);
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
