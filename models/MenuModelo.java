package models;

import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import modeloFiles.MenuFile;
import models.common.BaseModelo;
import models.common.ModeloUtil;

import java.io.*;
import SwingComponents.*;
import provedores.MenuProvedor;

public class MenuModelo extends BaseModelo {
    @CampoFormulario(
        descricao = "Menu Pai", 
        tipo = TipoCampo.COMBO,
        provider = MenuProvedor.class,
        largura = 100,
        linha = 1
    )
    private int menuPaiId;

    @CampoFormulario(
        descricao = "Codigo", 
        largura = 200,
        linha = 1
    )
    private StringBufferModelo codigo;

    @CampoFormulario(
        descricao = "Nome", 
        largura = 300,
        linha = 2
    )
    private StringBufferModelo nome;

    @CampoFormulario(
        tipo = TipoCampo.MULTTEXTO,
        descricao = "Descrição", 
        largura = 300,
        altura = 80,
        linha = 3
    )
    private StringBufferModelo descricao;

    @CampoFormulario(
        descricao = "Icone", 
        largura = 100,
        linha = 4
    )
    private StringBufferModelo icone;

    @CampoFormulario(
        descricao = "Caminho da Classe", 
        largura = 190,
        linha = 4
    )
    private StringBufferModelo caminhoClasse;

    @CampoFormulario(
        descricao = "Ordem", 
        largura = 140,
        linha = 5
    )
    private int ordem;

    @CampoFormulario(
        descricao = "Nivel Minimo Acesso", 
        largura = 150,
        linha = 5
    )
    private int nivelMinimoAcesso;
    private MenuModelo menuPai;

    public MenuModelo()
    {
        super();
        this.menuPaiId = 0;
        this.codigo = new StringBufferModelo(50);
        this.nome = new StringBufferModelo(30);
        this.descricao = new StringBufferModelo(100);
        this.icone = new StringBufferModelo(20);
        this.caminhoClasse = new StringBufferModelo(50);
        this.ordem = 0;
        this.nivelMinimoAcesso = 0;
    }
     public MenuModelo(int id, int menuPaiId, String codigo, String nome, String descricao, String icone, 
        String caminhoClasse, int ordem,int nivelMinimoAcesso) {
        super();
        setId(id);
        this.menuPaiId = menuPaiId;
        this.codigo = new StringBufferModelo(codigo, 50);
        this.nome = new StringBufferModelo(nome, 30);
        this.descricao = new StringBufferModelo(descricao, 100);
        this.icone = new StringBufferModelo(icone, 20);
        this.caminhoClasse = new StringBufferModelo(caminhoClasse, 50);
        this.ordem = ordem;
        this.nivelMinimoAcesso = nivelMinimoAcesso;
    }

    public int getMenuPaiId() {
        return menuPaiId;
    }
    public String getCodigo() {
        return codigo.toStringEliminatingSpaces();
    }
    public String getNome() {
        return nome.toStringEliminatingSpaces();
    }
    public String getDescricao() {
        return descricao.toStringEliminatingSpaces();
    }
    public String getIcone() {
        return icone.toStringEliminatingSpaces();
    }
    public String getCaminhoClasse() {
        return caminhoClasse.toStringEliminatingSpaces();
    }
    public int getOrdem() {
        return ordem;
    }
    public int getNivelMinimoAcesso() {
        return nivelMinimoAcesso;
    }

    public MenuModelo getMenuPai()
    {
       return MenuFile.instaciar().obterPorId(getMenuPaiId());
    }

    public void setMenuPaiId(int menuPaiId) {
        this.menuPaiId = menuPaiId;
    }
    public void setCodigo(String codigo) {
        this.codigo = new StringBufferModelo(codigo, 50);
    }
    public void setNome(String nome) {
        this.nome = new StringBufferModelo(nome, 30);
    }
    public void setDescricao(String descricao) {
        this.descricao = new StringBufferModelo(descricao, 100);
    }
    public void setIcone(String icone) {
        this.icone = new StringBufferModelo(icone, 20);
    }
    public void setCaminhoClasse(String caminhoClasse) {
        this.caminhoClasse = new StringBufferModelo(caminhoClasse, 50);
    }
    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }
    public void setNivelMinimoAcesso(int nivelMinimoAcesso) {
        this.nivelMinimoAcesso = nivelMinimoAcesso;
    }
    @Override
    public String toString()
    {
        String str = "Dados do Corte de Energia\n\n";

        str += "ID: "+ getId() + "\n";
        str += "MenuPaiID: "+ getMenuPaiId() + "\n";
        str += "Codigo: "+ getCodigo() + "\n";
        str += "Nome: "+ getNome() + "\n";
        str += "Descrição: "+ getDescricao() + "\n";
        str += "Icon: "+ getIcone() + "\n";
        str += "Caminho Classe: "+ getCaminhoClasse() + "\n";
        str += "Ordem: "+ getOrdem() + "\n";
        str += "Nivel Minimo de Acesso: "+ getNivelMinimoAcesso() + "\n";
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
            menuPaiId = stream.readInt();
            codigo.read(stream);;
            nome.read(stream);;
            descricao.read(stream);;
            icone.read(stream); 
            caminhoClasse.read(stream); 
            ordem = stream.readInt();
            nivelMinimoAcesso = stream.readInt();
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
            stream.writeInt(menuPaiId);
            codigo.write(stream);
            nome.write(stream);
            descricao.write(stream);
            icone.write(stream); 
            caminhoClasse.write(stream); 
            stream.writeInt(ordem);
            stream.writeInt(nivelMinimoAcesso);
        }
        catch(IOException ex)
		{
			ex.printStackTrace();
            throw new RuntimeException(ex);
		}
    }

    public void salvarDados()
    {
       new MenuFile(this).salvarDados();
    }

    public void atualizarDados()
    {
       new MenuFile(this).atualizarDados(getId(), this); 
    }
}
