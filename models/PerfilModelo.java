package models;

import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import modeloFiles.PerfilFile;
import models.common.BaseModelo;
import models.common.ModeloUtil;

import java.io.IOException;
import java.io.RandomAccessFile;

import SwingComponents.*;

public class PerfilModelo extends BaseModelo {

    @CampoFormulario(
        descricao = "Nome do perfil", 
        obrigatorio = true, 
        largura = 250
    )
    private StringBufferModelo nome;
    
    @CampoFormulario(
        tipo = TipoCampo.MULTTEXTO,
        descricao = "Descrição",
        obrigatorio = true,
        largura = 250, 
        altura = 60
    )
    private StringBufferModelo descricao;

    public PerfilModelo(){
        super();
        this.nome = new StringBufferModelo(50);
        this.descricao = new StringBufferModelo(50);
    }
    
    public PerfilModelo(int id, String nome, String descricao)
    {
       super();
       setId(id);
       this.nome = new StringBufferModelo(nome, 50);
       this.descricao = new StringBufferModelo(descricao, 50);
    }

    public String getNome() {
        return nome.toStringEliminatingSpaces();
    }
    public String getDescricao() {
        return descricao.toStringEliminatingSpaces();
    }
    
    public void setNome(String nome) {
        this.nome = new StringBufferModelo(nome, 50);
    }
    public void setDescricao(String descricao) {
        this.descricao = new StringBufferModelo(descricao, 50);
    }

    public String toString()
    {
        String str = "Dados do Perfil\n\n";

        str += "ID: "+ getId() + "\n";
        str += "Nome: "+ getNome() + "\n";
        str += "Descrição: "+ getDescricao() + "\n";

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
           nome.read(stream);
           descricao.read(stream);
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
           nome.write(stream);
           descricao.write(stream);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void salvarDados()
    {
        new PerfilFile(this).salvarDados();
    }
}
