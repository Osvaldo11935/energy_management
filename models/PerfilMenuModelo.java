package models;

import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import modeloFiles.*;
import models.common.BaseModelo;
import models.common.ModeloUtil;
import java.io.*;
import java.time.LocalDate;

import SwingComponents.*;
import provedores.BoleanoProvedor;
import provedores.MenuProvedor;
import provedores.PerfilProvedor;
import utils.DataMapper;

public class PerfilMenuModelo  extends BaseModelo{

    @CampoFormulario(
            descricao="Perfil",
            tipo=TipoCampo.COMBO,
            provider=PerfilProvedor.class,
            obrigatorio=true,
            largura = 200,
            linha = 1
    )
    private int perfilId;

    @CampoFormulario(
            descricao="Menu",
            tipo=TipoCampo.COMBO,
            provider=MenuProvedor.class,
            obrigatorio=true,
            largura = 210,
            linha = 1
    )
    private int menuId;

    @CampoFormulario(
            descricao="Pode visualizar",
            tipo=TipoCampo.COMBO,
            provider = BoleanoProvedor.class,
            largura = 100,
            linha = 2
    )
    private boolean podeVisualizar;

    @CampoFormulario(
            descricao="Pode criar",
            tipo=TipoCampo.COMBO,
            provider = BoleanoProvedor.class,
            largura = 100,
            linha = 2
    )
    private boolean podeCriar;

    @CampoFormulario(
            descricao="Pode editar",
            tipo=TipoCampo.COMBO,
            provider = BoleanoProvedor.class,
            largura = 100,
            linha = 2
    )
    private boolean podeEditar;

    @CampoFormulario(
            descricao="Pode eliminar",
            tipo=TipoCampo.COMBO,
            provider = BoleanoProvedor.class,
            largura = 100,
            linha = 2
    )
    private boolean podeEliminar;

    private DataModelo dataAtribuicao;

    private MenuModelo menu;
    private PerfilModelo perfil;

    public PerfilMenuModelo()
    {
        super();
        this.perfilId = 0;
        this.menuId = 0;
        this.podeVisualizar = false;
        this.podeCriar = false;
        this.podeEditar = false;
        this.podeEliminar = false;
        this.dataAtribuicao = new DataModelo();;
    }

    public PerfilMenuModelo(int id, int perfilId, int menuId, boolean podeVisualizar, boolean podeCriar,
            boolean podeEditar, boolean podeEliminar) {
        super();
        setId(id);
        this.perfilId = perfilId;
        this.menuId = menuId;
        this.podeVisualizar = podeVisualizar;
        this.podeCriar = podeCriar;
        this.podeEditar = podeEditar;
        this.podeEliminar = podeEliminar;
        this.dataAtribuicao = new DataModelo(DataMapper.normalizarData(LocalDate.now().toString()));
    }

    public int getPerfilId() {
        return perfilId;
    }

    public int getMenuId() {
        return menuId;
    }
    public boolean isPodeVisualizar() {
        return podeVisualizar;
    }
    public boolean isPodeCriar() {
        return podeCriar;
    }
    public boolean isPodeEditar() {
        return podeEditar;
    }
    public boolean isPodeEliminar() {
        return podeEliminar;
    }
    public DataModelo getDataAtribuicao() {
        return dataAtribuicao;
    }
    public MenuModelo getMenu()
    {
       MenuModelo menu = new MenuFile(new MenuModelo()).obterPorId(getMenuId());
       return  menu == null ? new MenuModelo() : menu;
    }
    public PerfilModelo getPerfil()
    {
       PerfilModelo perfil = new PerfilFile(new PerfilModelo()).obterPorId(getPerfilId());
       return perfil == null ? new PerfilModelo() : perfil;
    }
    public void setPerfilId(int perfilId) {
        this.perfilId = perfilId;
    }
    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
    public void setPodeVisualizar(boolean podeVisualizar) {
        this.podeVisualizar = podeVisualizar;
    }
    public void setPodeCriar(boolean podeCriar) {
        this.podeCriar = podeCriar;
    }
    public void setPodeEditar(boolean podeEditar) {
        this.podeEditar = podeEditar;
    }
    public void setPodeEliminar(boolean podeEliminar) {
        this.podeEliminar = podeEliminar;
    }
    public void setDataAtribuicao(String dataAtribuicao) {
        this.dataAtribuicao = new DataModelo(DataMapper.normalizarData(dataAtribuicao));
    }

    public String toString()
    {
        String str = "Dados do Perfil\n\n";

        str += "ID: "+ getId() + "\n";
        str += "PerfilID: "+ getPerfilId() + "\n";
        str += "MenuID: "+ getMenuId() + "\n";
        str += "Pode Visualizar: "+ isPodeVisualizar() + "\n";
        str += "Pode Criar: "+ isPodeCriar() + "\n";
        str += "Pode Editar: "+ isPodeEditar() + "\n";
        str += "Pode Eliminar: "+ isPodeEliminar() + "\n";

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
           perfilId = stream.readInt();
           menuId = stream.readInt();
           podeVisualizar = stream.readBoolean();
           podeCriar = stream.readBoolean();
           podeEditar = stream.readBoolean();
           podeEliminar = stream.readBoolean();
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
           stream.writeInt(perfilId);
           stream.writeInt(menuId);
           stream.writeBoolean(podeVisualizar);
           stream.writeBoolean(podeCriar);
           stream.writeBoolean(podeEditar);
           stream.writeBoolean(podeEliminar);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void salvarDados()
    {
        new PerfilMenuFile(this).salvarDados();
    }
    public void atualizarDados() {
        new PerfilMenuFile(this).atualizarDados(getId(), this);
    }
}
