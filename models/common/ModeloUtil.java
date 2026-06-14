package models.common;

import java.lang.reflect.Field;

import SwingComponents.*;

public class ModeloUtil {

    public static <T> long sizeOf(T obj)
    {
       long _sizeOf = 0;
       try
       {
           
          for(Field field : obj.getClass().getDeclaredFields())
          {
            field.setAccessible(true);
            Class<?> tipo = field.getType();
            if(tipo == StringBufferModelo.class)
            {
                StringBufferModelo sb = (StringBufferModelo) field.get(obj);
                if(sb != null)
                   _sizeOf += sb.sizeof();
                else
                   _sizeOf += tamanhoTipo(tipo);
            }
            else
                _sizeOf += tamanhoTipo(tipo);
          }
       }
       catch(Exception ex)
       {
          ex.printStackTrace();
       }
       return _sizeOf;
    }
    private static long tamanhoTipo(Class<?> tipo)
    {
        int tam = 0;
        if(tipo == byte.class)
           tam = 1;
        if(tipo == boolean.class)
           tam = 1;
        if(tipo == short.class)
            tam = 2;
        if(tipo == char.class)
            tam = 2;
        if(tipo == int.class)
            tam = 4;
        if(tipo == float.class)
            tam = 4;
        if(tipo == long.class)
            tam = 8;
        if(tipo == double.class)
            tam = 8;

        return tam; 
    }
}
