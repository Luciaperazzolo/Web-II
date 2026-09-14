package com.ejemplo.tp1_APIREST.Exception;

public class ApiExternaException extends RuntimeException {
    public ApiExternaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
