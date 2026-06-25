package modeloFiles.common;

import javax.swing.*;
import java.io.*;
import java.util.*;
import SwingComponents.*;
import models.common.BaseModelo;

public abstract class CrudFile<T extends BaseModelo> extends ObjectsFile {

    protected T modelo;

    private final Class<T> classe;

    public CrudFile(
            String caminho,
            T modelo,
            Class<T> classe) {

        super(caminho,modelo);

        this.modelo = modelo;
        this.classe = classe;
    }

    protected T criarInstancia()
            throws Exception {

        return classe
                .getDeclaredConstructor()
                .newInstance();
    }
    public void salvarDados() {

        try {

            stream.seek(
                    stream.length());

            modelo.write(
                    stream);

            JOptionPane.showMessageDialog(
                    null,
                    "Registo salvo com sucesso");

        } catch (IOException ex) {

            ex.printStackTrace();
        }
    }

    public List<T> listar() {

        List<T> lista =
                new ArrayList<>();

        try {

            stream.seek(4);

            while (
                    stream.getFilePointer()
                            < stream.length()) {

                T obj =
                        criarInstancia();

                obj.read(stream);

                if (obj.isActivo()) {
                    lista.add(obj);
                }
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return lista;
    }

    public T obterPorId(
            int id) {

        try {

            stream.seek(4);

            while (
                    stream.getFilePointer()
                            < stream.length()) {

                T obj =
                        criarInstancia();

                obj.read(stream);

                if (
                        obj.getId() == id
                                &&
                                obj.isActivo()) {

                    return obj;
                }
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return null;
    }

    public void atualizarDados(int id, T novo) {

        try {

            stream.seek(4);

            while (stream.getFilePointer()< stream.length()) {

                long pos = stream.getFilePointer();

                T atual = criarInstancia();

                atual.read(stream);

                if (atual.getId() == id) {

                    novo.setId(id);

                    stream.seek(pos);

                    novo.write(stream);

                    return;
                }
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    public void remover(int id) {

        T obj =obterPorId(id);

        if (obj == null)
            return;

        obj.setActivo(false);

        atualizarDados(id,obj);
    }
}