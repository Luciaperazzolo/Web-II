package com.ejemplo.tp1_APIREST.Exception;

public class ConflictoException extends RuntimeException{
    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}
