package com.ejemplo.tp1_APIREST.Service;

import java.util.List;

import org.springframework.stereotype.Service; 
import org.springframework.web.client.RestClient;

import com.ejemplo.tp1_APIREST.DTO.DummyJsonResponse;
import com.ejemplo.tp1_APIREST.DTO.ProductoDTO;
import com.ejemplo.tp1_APIREST.DTO.ProductoExternoDTO;

@Service
public class ProductoService {
    private final RestClient restClient; //Guardamos el objeto que nos permite hacer peticiones HTTP a otras APIs

    //Constructor
    public ProductoService(RestClient restClient) { 
        this.restClient = restClient; 
    }

    //Método que obtiene los productos de la API externa
    public List<ProductoDTO> obtenerProductos() {
        DummyJsonResponse respuesta = restClient.get()
            .uri("/products")
            .retrieve()
            .body(DummyJsonResponse.class);

        return respuesta.getProducts()
            .stream()
            .map(this::mapearProducto)
            .toList();
    }

    //Método que mapea un ProductoExternoDTO a un ProductoDTO
    private ProductoDTO mapearProducto(ProductoExternoDTO productoExterno) { 
        ProductoDTO producto = new ProductoDTO(); 
        producto.setId(productoExterno.getId()); 
        producto.setNombre(productoExterno.getTitle()); 
        producto.setDescripcion(productoExterno.getDescription()); 
        producto.setPrecio(productoExterno.getPrice()); 
        producto.setCategoria(productoExterno.getCategory()); 
        return producto; 
    }

    //Método que obtiene un producto por su ID de la API externa
    public ProductoDTO obtenerProductoPorId(Long id) {
        ProductoExternoDTO productoExterno = restClient.get()
            .uri("/products/{id}", id)
            .retrieve()
            .body(ProductoExternoDTO.class);

        return mapearProducto(productoExterno);
    }
}
