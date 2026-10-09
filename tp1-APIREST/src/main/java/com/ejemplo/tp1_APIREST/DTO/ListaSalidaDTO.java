package com.ejemplo.tp1_APIREST.DTO;

public class ListaSalidaDTO {
    private Long id;
    private String nombre;

    public ListaSalidaDTO() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
