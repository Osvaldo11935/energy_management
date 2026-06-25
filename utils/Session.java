package utils;

import models.UsuarioModelo;

public class Session {
    private static int diaAtraso;
    private static int diaToleranca;
    private static String nomeCombo;
    private static Object valorSelecionadoCombo;
    private static UsuarioModelo usuarioLogado;
    


    public static void setDiaAtraso(int dia) {
        diaAtraso = dia;
    }

    public static void setDadoCombo(String nome, Object valor) {
        nomeCombo = nome;
        valorSelecionadoCombo = valor;
    }


    public static void setDiaToleranca(int dia) {
        diaToleranca = dia;
    }
    public static void setUsuario(UsuarioModelo usuario) {
        usuarioLogado = usuario;
    }

    public static int getDiaToleranca() {
        return diaToleranca;
    }

    public static int getDiaAtraso() {
        return diaAtraso;
    }
    
    public static Object getValorCombo() {
        return valorSelecionadoCombo;
    }
    public static String getNomeCombo() {
        return nomeCombo;
    }

    public static UsuarioModelo getUsuario() {
        return usuarioLogado;
    }

    public static void limpar() {
        usuarioLogado = null;
    }

    public static boolean estaLogado() {
        return usuarioLogado != null;
    }
}