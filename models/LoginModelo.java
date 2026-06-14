package models;

import anotacoes.CampoFormulario;
import anotacoes.TipoCampo;
import modeloFiles.UsuarioFile;
import SwingComponents.*;

public class LoginModelo {
    @CampoFormulario(
        descricao = "E-mail", 
        obrigatorio = true, 
        tipo = TipoCampo.EMAIL,
        largura = 250
    )
    private StringBufferModelo email;

    @CampoFormulario(
        descricao = "Senha", 
        obrigatorio = true, 
        tipo = TipoCampo.SENHA,
        largura = 250
    )
    private StringBufferModelo palavraPass;
    
    public LoginModelo()
    {
        this.email = new StringBufferModelo(30);
        this.palavraPass = new StringBufferModelo(10);
    }

    public LoginModelo(String email, String palavraPass)
    {
        this.email = new StringBufferModelo(email, 30);
        this.palavraPass = new StringBufferModelo(palavraPass, 10);
    }

    public String getEmail() {
        return email.toStringEliminatingSpaces();
    }
    public String getPalavraPass() {
        return palavraPass.toStringEliminatingSpaces();
    }

    public void setEmail(String email) {
        this.email = new StringBufferModelo(email, 30);
    }

    public void setPalavraPass(String palavraPass) {
        this.palavraPass = new StringBufferModelo(palavraPass, 10);
    }

    public String toString()
    {
        String str = "Dados do Login\n\n";

        str += "E-mail: "+ getEmail() + "\n";

        return str;
    }

    public UsuarioModelo login()
    {
        return new UsuarioFile(new UsuarioModelo()).autenticar(getEmail(), getPalavraPass());
    }
}
