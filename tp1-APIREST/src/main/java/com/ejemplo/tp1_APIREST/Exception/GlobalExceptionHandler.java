package com.ejemplo.tp1_APIREST.Exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.ejemplo.tp1_APIREST.Exception.ApiExternaException;

@RestControllerAdvice
public class GlobalExceptionHandler {
      @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(
            MethodArgumentNotValidException ex) {

        Map<String, String> detalles = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                detalles.put(error.getField(), error.getDefaultMessage())
        );

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("status", HttpStatus.BAD_REQUEST.value());
        respuesta.put("error", "Error de validación");
        respuesta.put("details", detalles);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarRecursoNoEncontrado(
        RecursoNoEncontradoException ex) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("status", HttpStatus.NOT_FOUND.value());
        respuesta.put("error", "Recurso no encontrado");
        respuesta.put("message", ex.getMessage());

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(respuesta);
    }

    @ExceptionHandler(ApiExternaException.class)
    public ResponseEntity<Map<String, Object>> manejarApiExterna(
        ApiExternaException ex) {

        Map<String, Object> respuesta = new HashMap<>();

        respuesta.put("status", HttpStatus.BAD_GATEWAY.value());
        respuesta.put("error", "Error en la API externa");
        respuesta.put("message", ex.getMessage());

        return ResponseEntity
          .status(HttpStatus.BAD_GATEWAY)
          .body(respuesta);
    }
}
