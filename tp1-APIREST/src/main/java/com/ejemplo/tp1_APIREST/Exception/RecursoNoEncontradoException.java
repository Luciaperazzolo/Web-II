package com.ejemplo.tp1_APIREST.Exception;

public class RecursoNoEncontradoException extends RuntimeException {
     public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
