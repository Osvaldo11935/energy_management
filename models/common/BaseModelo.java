package models.common;

import java.io.*;
import SwingComponents.*;
public abstract class BaseModelo implements RegistGeneric
{
    protected int id;

    protected boolean activo;

    public BaseModelo()
    {
        id = 0;
        activo = true;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public boolean isActivo()
    {
        return activo;
    }

    public void setActivo(boolean activo)
    {
        this.activo = activo;
    }

    protected void writeBase(RandomAccessFile stream)throws IOException
    {
        stream.writeInt(id);

        stream.writeBoolean(activo);
    }

    protected void readBase(RandomAccessFile stream)throws IOException
    {
        id =stream.readInt();
        activo =stream.readBoolean();
    }
}