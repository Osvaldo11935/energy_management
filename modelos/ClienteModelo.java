package modelos;

import java.io.*;
import java.time.LocalDate;

import SwingComponents.*;
import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import enums.TipoClienteEnum;
import enums.TipoContratoEnum;
import modeloFiles.AreaDistribuicaoFile;
import modeloFiles.ClienteFile;
import modeloFiles.UsuarioFile;
import modelos.common.BaseModelo;
import modelos.common.ModeloUtil;
import provedores.AreaDistribuicaoProvedor;
import utils.DataMapper;

public class ClienteModelo extends BaseModelo {

    private int usuarioId;

    @CampoFormulario(
        descricao = "Tipo de Contrato",
        tipo = TipoCampo.COMBO,
        largura = 200,
        linha = 1,
        enumType = TipoContratoEnum.class
    )
    private StringBufferModelo tipoContrato;

    @CampoFormulario(
        descricao = "Tipo de Cliente",
        tipo = TipoCampo.COMBO,
        largura = 200,
        linha = 1,
        enumType = TipoClienteEnum.class
    )
    private StringBufferModelo tipoCliente;

    @CampoFormulario(
        descricao = "Area de Distribuição",
        tipo = TipoCampo.COMBO,
        largura = 400,
        linha = 2,
        provider = AreaDistribuicaoProvedor.class
    )
    private int areaDistribuicaoId;
    private DataModelo dataCadastro;
    private double saldoDevedor;
    private double creditoDisponivel;
    private double limiteConsumoMensal;

    @CampoFormulario(
        tipo = TipoCampo.MULTTEXTO,
        descricao = "Observações",
        largura = 400,
        linha = 3,
        altura = 80
    )
    private StringBufferModelo observacoes;

    private AreaDistribuicaoModelo areaDistribuicao;
    private UsuarioModelo usuario;
    public ClienteModelo()
    {
        super();
        this.usuarioId = 0;
        this.tipoCliente = new StringBufferModelo(30);
        this.tipoContrato = new StringBufferModelo(30);
        this.areaDistribuicaoId = 0;
        this.dataCadastro = new DataModelo();
        this.saldoDevedor = 0.0;
        this.creditoDisponivel = 0.0;
        this.limiteConsumoMensal = 0.0;
        this.observacoes = new StringBufferModelo(100);
    }
    public ClienteModelo(int id, int usuarioId, String tipoCliente, String tipoContrato, int areaDistribuicaoId, double saldoDevedor, 
        double creditoDisponivel, double limiteConsumoMensal, String observacoes) {
        super();
        setId(id);
        this.usuarioId = usuarioId;
        this.tipoCliente = new StringBufferModelo(tipoCliente, 30);
        this.tipoContrato = new StringBufferModelo(tipoContrato, 30);
        this.areaDistribuicaoId = areaDistribuicaoId;
        this.dataCadastro = new DataModelo(DataMapper.normalizarData(LocalDate.now().toString()));
        this.saldoDevedor = saldoDevedor;
        this.creditoDisponivel = creditoDisponivel;
        this.limiteConsumoMensal = limiteConsumoMensal;
        this.observacoes = new StringBufferModelo(observacoes,100);
    }
    public int getUsuarioId() {
        return usuarioId;
    }
    public String getTipoContrato() {
        return tipoContrato.toStringEliminatingSpaces();
    }
    public String getTipoCliente() {
        return tipoCliente.toStringEliminatingSpaces();
    }
    public int getAreaDistribuicaoId() {
        return areaDistribuicaoId;
    }
    public String getDataCadastro() {
        return dataCadastro.toString();
    }
    public double getSaldoDevedor() {
        return saldoDevedor;
    }
    public double getCreditoDisponivel() {
        return creditoDisponivel;
    }
    public double getLimiteConsumoMensal() {
        return limiteConsumoMensal;
    }
    public String getObservacoes() {
        return observacoes.toStringEliminatingSpaces();
    }
    public AreaDistribuicaoModelo getAreaDistribuicao() {
        return AreaDistribuicaoFile.instaciar().obterPorId(getAreaDistribuicaoId());
    }

    public UsuarioModelo getUsuario() {
        return UsuarioFile.instaciar().obterPorId(getUsuarioId());
    }
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = new StringBufferModelo(tipoContrato, 30);
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = new StringBufferModelo(tipoCliente, 30);
    }
    public void setAreaDistribuicaoId(int areaDistribuicaoId) {
        this.areaDistribuicaoId = areaDistribuicaoId;
    }
    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = new DataModelo(dataCadastro);
    }
    public void setSaldoDevedor(double saldoDevedor) {
        this.saldoDevedor = saldoDevedor;
    }
    public void setCreditoDisponivel(double creditoDisponivel) {
        this.creditoDisponivel = creditoDisponivel;
    }
    public void setLimiteConsumoMensal(double limiteConsumoMensal) {
        this.limiteConsumoMensal = limiteConsumoMensal;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = new StringBufferModelo(observacoes, 100);
    }


    @Override
    public String toString()
    {
        String str = "Dados do cliente\n\n";

        str += "ID: "+ getId() + "\n";
        str += "UsuarioID: "+ getUsuarioId() + "\n";
        str += "Tipo Cliente: "+ getTipoCliente() + "\n";
        str += "Tipo Contrato: "+ getTipoContrato() + "\n";
        str += "AreaDistribuicao: "+ getAreaDistribuicaoId() + "\n";
        str += "Data Do Cadastro: "+ getDataCadastro() + "\n";
        str += "Saldo Devedor: "+ getSaldoDevedor() + "\n";
        str += "Credito Disponivel: "+ getCreditoDisponivel() + "\n";
        str += "Limite de Consumo Mensal: "+ getLimiteConsumoMensal() + "\n";
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
            usuarioId = stream.readInt();
            tipoCliente.read(stream);
            tipoContrato.read(stream);
            areaDistribuicaoId = stream.readInt();
            dataCadastro.read(stream);;
            saldoDevedor = stream.readDouble();
            creditoDisponivel = stream.readDouble();
            limiteConsumoMensal = stream.readDouble();
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
            stream.writeInt(usuarioId);
            tipoCliente.write(stream);
            tipoContrato.write(stream);
            stream.writeInt(areaDistribuicaoId);
            dataCadastro.write(stream);
            stream.writeDouble(saldoDevedor);
            stream.writeDouble(creditoDisponivel);
            stream.writeDouble(limiteConsumoMensal);
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
       new ClienteFile(this).salvarDados();
    }

    public void atualizarDados()
    {
       new ClienteFile(this).atualizarDados(getId(), this); 
    }
}
