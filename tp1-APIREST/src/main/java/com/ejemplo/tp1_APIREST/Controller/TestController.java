package com.ejemplo.tp1_APIREST.Controller;

import org.springframework.web.bind.annotation.GetMapping; //Esta anotación indica que el método va a manejar peticiones HTTP GET
import org.springframework.web.bind.annotation.RestController; //Esta clase va a recibir peticiones HTTP y devolver respuestas de una API

@RestController
public class TestController {
    @GetMapping("/hola")
    public String hola() {
        return "Hola desde Spring Boot";
    }
}
