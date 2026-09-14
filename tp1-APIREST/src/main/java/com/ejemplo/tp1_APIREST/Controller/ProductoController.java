package com.ejemplo.tp1_APIREST.Controller;

import com.ejemplo.tp1_APIREST.DTO.ProductoDTO; 
import com.ejemplo.tp1_APIREST.Service.ProductoService; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) { 
        this.productoService = productoService; 
    } 
    
    @Operation(summary = "Obtener todos los productos")
    @GetMapping 
    public List<ProductoDTO> obtenerProductos() { 
        return productoService.obtenerProductos(); 
    }

    @Operation(summary = "Obtener un producto por ID")
    @GetMapping("/{id}")
    public ProductoDTO obtenerProductoPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id);
    }

}
