package com.ejemplo.tp1_APIREST.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class FavoritoEntradaDTO {
    @NotNull(message = "El productoId es obligatorio")
    @Positive(message = "El productoId debe ser mayor a 0")
    private Long productoId;

    @NotBlank(message = "La nota es obligatoria")
    @Size(max = 500, message = "La nota no puede superar los 500 caracteres")
    private String nota;


    public FavoritoEntradaDTO() {
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
