package models;

import java.io.*;
import SwingComponents.*;
import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import modeloFiles.AtalhoFile;
import modeloFiles.ClienteFile;
import modeloFiles.MenuFile;
import models.common.BaseModelo;
import models.common.ModeloUtil;
import provedores.MenuPerfilProvedor;
import provedores.TecladoProvedor;


public class AtalhoModelo extends BaseModelo {

    @CampoFormulario(
        descricao = "Menu",
        largura = 300,
        linha = 1,
        tipo = TipoCampo.COMBO,
        provider = MenuPerfilProvedor.class
    )
    private int menuId;

    @CampoFormulario(
        descricao = "Nome Atalho",
        largura = 300,
        linha = 2
    )
    private StringBufferModelo nome;

    @CampoFormulario(
        descricao = "Descrição Atalho",
        largura = 300,
        linha = 3,
        altura = 80,
        tipo = TipoCampo.MULTTEXTO
    )
    private StringBufferModelo descricao;
    @CampoFormulario(
        descricao = "Teclado de Atalho",
        largura = 300,
        linha = 4,
        tipo = TipoCampo.COMBO,
        provider = TecladoProvedor.class
    )
    private StringBufferModelo teclado;

    private int usuarioId;

    private MenuModelo menu;

    public AtalhoModelo()
    {
       super();
       this.menuId = 0;
       this.nome =  new StringBufferModelo( 30);
       this.descricao = new StringBufferModelo( 100);
       this.teclado = new StringBufferModelo(20);
       this.usuarioId = 0;

    }

    public AtalhoModelo(int id, int menuId, String nome, String descricao, String teclado,  int usuarioId) {
        super();
        setId(id);
        this.menuId = menuId;
        this.nome =  new StringBufferModelo(nome, 30);
        this.descricao = new StringBufferModelo(descricao, 100);
        this.teclado = new StringBufferModelo(teclado, 20);
        this.usuarioId = usuarioId;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getNome() {
        return nome.toStringEliminatingSpaces();
    }

    public String getDescricao() {
        return descricao.toStringEliminatingSpaces();
    }
    
    public int getUsuarioId() {
        return usuarioId;
    }

    public String getTeclado() {
        return teclado.toStringEliminatingSpaces();
    }

    public MenuModelo getMenu() {
        return MenuFile.instaciar().obterPorId(getMenuId());
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
    
    public void setNome(String nome) {
        this.nome = new StringBufferModelo(nome, 30);
    }

    public void setDescricao(String descricao) {
        this.descricao = new StringBufferModelo(descricao, 100);
    }

    public void setTeclado(String teclado) {
        this.teclado = new StringBufferModelo(teclado, 20);
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString()
    {
        String str = "Dados do Atalho\n\n";

        str += "ID: "+ getId() + "\n";
        str += "Nome: "+ getNome() + "\n";
        str += "Descrição: "+ getDescricao() + "\n";
        str += "MenuID: "+ getMenuId() + "\n";
        str += "Teclado: "+ getTeclado() + "\n";
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
            menuId = stream.readInt();
            nome.read(stream);
            descricao.read(stream);
            teclado.read(stream);
            usuarioId = stream.readInt();
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
            stream.writeInt(menuId);
            nome.write(stream);
            descricao.write(stream);
            teclado.write(stream);
            stream.writeInt(usuarioId);
        }
        catch(IOException ex)
		{
			ex.printStackTrace();
            throw new RuntimeException(ex);
		}
    }

    public void salvarDados()
    {
       new AtalhoFile(this).salvarDados();
    }

    public void atualizarDados()
    {
       new AtalhoFile(this).atualizarDados(getId(), this); 
    }
}
