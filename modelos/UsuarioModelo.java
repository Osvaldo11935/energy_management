package modelos;

import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import modeloFiles.PerfilFile;
import modeloFiles.UsuarioFile;
import modelos.common.BaseModelo;
import modelos.common.ModeloUtil;
import provedores.PerfilProvedor;
import SwingComponents.*;

import javax.swing.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class UsuarioModelo extends BaseModelo{
    
    @CampoFormulario(
        descricao = "Nome de Usuário", 
        obrigatorio = true,
        largura = 200,
        linha = 1
    )
    private StringBufferModelo nomeUsuario;

    @CampoFormulario(
        descricao = "E-mail", 
        tipo = TipoCampo.EMAIL,
        largura = 200,
        linha = 1
    )
    private StringBufferModelo email;

    @CampoFormulario(
        descricao = "Número de Telefone", 
        tipo = TipoCampo.TELEFONE,
        largura = 200,
        linha = 2
    )
    private StringBufferModelo numeroTelefone;

    @CampoFormulario(
        descricao = "Palavra-passe", 
        tipo = TipoCampo.SENHA,
        largura = 200,
        linha = 2
    )
    private StringBufferModelo palavraPass;
    @CampoFormulario(
        descricao = "Perfil",
        tipo = TipoCampo.COMBO,
        largura = 400,
        linha = 3,
        provider  = PerfilProvedor.class
    )
    private int perfilId;

    private PerfilModelo perfil;

    public UsuarioModelo()
    {
        super();
        this.nomeUsuario = new StringBufferModelo(15);
        this.email = new StringBufferModelo(30);
        this.numeroTelefone = new StringBufferModelo(12);
        this.palavraPass = new StringBufferModelo(10);
        this.perfilId = 0;
    }

    public UsuarioModelo(int id, String nomeUsuario, String email, String numeroTelefone, String palavraPass, int perfilId)
    {  
        super();
        setId(id);
        this.nomeUsuario = new StringBufferModelo(nomeUsuario, 15);
        this.email = new StringBufferModelo(email, 30);
        this.numeroTelefone = new StringBufferModelo(numeroTelefone, 12);
        this.palavraPass = new StringBufferModelo(palavraPass, 10);
        this.perfilId = perfilId;
    }

    public String getNomeUsuario() {
        return nomeUsuario.toStringEliminatingSpaces();
    }

    public String getEmail() {
        return email.toStringEliminatingSpaces();
    }

    public String getNumeroTelefone() {
        return numeroTelefone.toStringEliminatingSpaces();
    }

    public String getPalavraPass() {
        return palavraPass.toStringEliminatingSpaces();
    }
    
    public int getPerfilId() {
        return perfilId;
    }

    public PerfilModelo getPerfil()
    {
        return new PerfilFile(new PerfilModelo()).obterPorId(getPerfilId());
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = new StringBufferModelo(nomeUsuario, 15);
    }
    
    public void setEmail(String email) {
        this.email = new StringBufferModelo(email, 30);
    }
    
    public void setNumeroTelefone(String numeroTelefone) {
        this.numeroTelefone = new StringBufferModelo(numeroTelefone, 12);
    }
    
    public void setPalavraPass(String palavraPass) {
        this.palavraPass = new StringBufferModelo(palavraPass, 10);
    }
    
    public void setPerfilId(int perfilId) {
        this.perfilId = perfilId;
    }
    
    public boolean ehCliente()
    {
        return getPerfil().getNome().toLowerCase().equals("cliente");
    }

    public boolean ehTecnico()
    {
        return getPerfil().getNome().toLowerCase().equals("tecnico");
    }

    @Override
    public String toString()
    {
        String str = "Dados do usuario\n\n";

        str += "ID: "+ getId() + "\n";
        str += "Perfil ID: "+ getPerfilId() + "\n";
        str += "Nome do Usuario: "+ getNomeUsuario() + "\n";
        str += "E-mail: "+ getEmail() + "\n";
        str += "Nº de Telefone: "+ getNumeroTelefone() + "\n";

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
            nomeUsuario.read(stream);
            email.read(stream);
            numeroTelefone.read(stream);
            palavraPass.read(stream);
            perfilId = stream.readInt();
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
            nomeUsuario.write(stream);
            email.write(stream);
            numeroTelefone.write(stream);
            palavraPass.write(stream);
            stream.writeInt(perfilId);
        }
        catch(IOException ex)
		{
			ex.printStackTrace();
            throw new RuntimeException(ex);
		}
    }

    public void salvarDados()
    {
       new UsuarioFile(this).salvarDados();
    }

    public void atualizarDados()
    {
       new UsuarioFile(this).atualizarDados(getId(), this); 
    }
}
