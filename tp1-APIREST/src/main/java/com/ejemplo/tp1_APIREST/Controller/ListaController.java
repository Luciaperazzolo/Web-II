package com.ejemplo.tp1_APIREST.Controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.tp1_APIREST.DTO.FavoritoSalidaDTO;
import com.ejemplo.tp1_APIREST.DTO.ListaEntradaDTO;
import com.ejemplo.tp1_APIREST.DTO.ListaSalidaDTO;
import com.ejemplo.tp1_APIREST.DTO.MoverFavoritosDTO;
import com.ejemplo.tp1_APIREST.Model.Lista;
import com.ejemplo.tp1_APIREST.Service.ListaService;
import com.ejemplo.tp1_APIREST.DTO.FavoritoSalidaDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/listas")
public class ListaController {
    private final ListaService listaService;

    public ListaController(ListaService listaService) {
        this.listaService = listaService;
    }

    @Operation(summary = "Crear una lista")
    @PostMapping
    public ResponseEntity<ListaSalidaDTO> crear(
            @Valid @RequestBody ListaEntradaDTO dto) {

        Lista lista = listaService.crear(dto.getNombre());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(convertirASalida(lista));
    }

    @Operation(summary = "Listar todas las listas")
    @GetMapping
    public ResponseEntity<List<ListaSalidaDTO>> buscarTodos() {

        List<ListaSalidaDTO> listas = listaService.buscarTodos()
                .stream()
                .map(this::convertirASalida)
                .toList();

        return ResponseEntity.ok(listas);
    }

    @Operation(summary = "Obtener una lista por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ListaSalidaDTO> buscarPorId(
            @PathVariable Long id) {

        Lista lista = listaService.buscarPorId(id);

        return ResponseEntity.ok(convertirASalida(lista));
    }

    @Operation(summary = "Obtener los favoritos de una lista")
    @GetMapping("/{id}/favoritos")
    public ResponseEntity<List<FavoritoSalidaDTO>> buscarFavoritos(@PathVariable Long id) {
        return ResponseEntity.ok(listaService.buscarFavoritos(id));
    }

    private ListaSalidaDTO convertirASalida(Lista lista) {

        ListaSalidaDTO dto = new ListaSalidaDTO();

        dto.setId(lista.getId());
        dto.setNombre(lista.getNombre());

        return dto;
    }

    @Operation(summary = "Eliminar una lista")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Lista eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "La lista no existe"),
        @ApiResponse(responseCode = "409", description = "La lista tiene favoritos asociados")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        listaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Mover favoritos a otra lista y eliminar la lista de origen")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Favoritos movidos correctamente y lista de origen eliminada"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "No existe la lista de origen o destino"),
        @ApiResponse(responseCode = "409", description = "La lista de origen y destino son la misma")
    })
    @PostMapping("/{origenId}/mover-favoritos")
    public ResponseEntity<Void> moverFavoritos(
        @PathVariable Long origenId,
        @Valid @RequestBody MoverFavoritosDTO datos) {

        listaService.moverFavoritos(origenId, datos.getDestinoId());

        return ResponseEntity.noContent().build();
    }
}
