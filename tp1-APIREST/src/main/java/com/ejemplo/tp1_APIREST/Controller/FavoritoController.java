package com.ejemplo.tp1_APIREST.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.tp1_APIREST.DTO.FavoritoEntradaDTO;
import com.ejemplo.tp1_APIREST.DTO.FavoritoSalidaDTO;
import com.ejemplo.tp1_APIREST.Service.FavoritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {
    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    @Operation(summary = "Crear un favorito")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Favorito creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<FavoritoSalidaDTO> crear(
            @Valid @RequestBody FavoritoEntradaDTO dto) {

        FavoritoSalidaDTO favorito = favoritoService.crear(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(favorito);
    }

    @Operation(summary = "Listar todos los favoritos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Favoritos obtenidos correctamente")
    })
    @GetMapping
    public ResponseEntity<List<FavoritoSalidaDTO>> buscarTodos() {

        return ResponseEntity.ok(favoritoService.buscarTodos());
    }

    @Operation(summary = "Obtener un favorito por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Favorito encontrado"),
        @ApiResponse(responseCode = "404", description = "Favorito no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FavoritoSalidaDTO> buscarPorId(
        @PathVariable Long id) {

        return ResponseEntity.ok(favoritoService.buscarPorId(id));
    }

   @Operation(summary = "Actualizar un favorito")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Favorito actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Favorito no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FavoritoSalidaDTO> actualizar(
        @PathVariable Long id,
        @Valid @RequestBody FavoritoEntradaDTO dto) {

        FavoritoSalidaDTO favorito = favoritoService.actualizar(id, dto);

        return ResponseEntity.ok(favorito);
    }

    @Operation(summary = "Eliminar un favorito")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Favorito eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Favorito no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        favoritoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
