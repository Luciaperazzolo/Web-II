package com.ejemplo.tp1_APIREST.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MoverFavoritosDTO {
    @NotNull(message = "El destinoId es obligatorio")
    @Positive(message = "El destinoId debe ser mayor que cero")
    private Long destinoId;

    public Long getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(Long destinoId) {
        this.destinoId = destinoId;
    }
}
