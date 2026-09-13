package com.ejemplo.tp1_APIREST.DTO;

import java.util.List;

public class DummyJsonResponse {
    private List<ProductoExternoDTO> products;

    //Constructor vacío
    public DummyJsonResponse() {
    }

    //Getters y Setters
    public List<ProductoExternoDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductoExternoDTO> products) {
        this.products = products;
    }
}
