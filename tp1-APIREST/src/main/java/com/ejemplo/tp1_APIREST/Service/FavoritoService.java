package com.ejemplo.tp1_APIREST.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ejemplo.tp1_APIREST.DTO.FavoritoEntradaDTO;
import com.ejemplo.tp1_APIREST.DTO.FavoritoSalidaDTO;
import com.ejemplo.tp1_APIREST.Exception.RecursoNoEncontradoException;
import com.ejemplo.tp1_APIREST.Model.Favorito;
import com.ejemplo.tp1_APIREST.Repository.FavoritoRepository;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;

    public FavoritoService(FavoritoRepository favoritoRepository) {
        this.favoritoRepository = favoritoRepository;
    }

    private Long siguienteId = 1L;

    public FavoritoSalidaDTO crear(FavoritoEntradaDTO dto) {

        Favorito favorito = new Favorito();

        favorito.setId(siguienteId);
        siguienteId++;

        favorito.setProductoId(dto.getProductoId());
        favorito.setNota(dto.getNota());
        favorito.setFechaAgregado(LocalDateTime.now());

        favoritoRepository.guardar(favorito);

        return convertirASalida(favorito);
    }

    public List<FavoritoSalidaDTO> buscarTodos() {

        return favoritoRepository.buscarTodos()
            .stream()
            .map(this::convertirASalida)
            .toList();
    }

    public FavoritoSalidaDTO buscarPorId(Long id) {

        Favorito favorito = favoritoRepository.buscarPorId(id)
            .orElseThrow(() ->
                new RecursoNoEncontradoException(
                    "No existe un favorito con el id " + id
                )
            );

        return convertirASalida(favorito);
    }

    public FavoritoSalidaDTO actualizar(
            Long id,
            FavoritoEntradaDTO dto) {

        Favorito favorito = favoritoRepository.buscarPorId(id)
            .orElseThrow(() ->
                new RecursoNoEncontradoException(
                    "No existe un favorito con el id " + id
                )
            );

        favorito.setProductoId(dto.getProductoId());
        favorito.setNota(dto.getNota());

        return convertirASalida(favorito);
    }

    public void eliminar(Long id) {

        Favorito favorito = favoritoRepository.buscarPorId(id)
            .orElseThrow(() ->
                new RecursoNoEncontradoException(
                    "No existe un favorito con el id " + id
                )
            );

        favoritoRepository.eliminarPorId(favorito.getId());
    }

    private FavoritoSalidaDTO convertirASalida(Favorito favorito) {

        FavoritoSalidaDTO dto = new FavoritoSalidaDTO();

        dto.setId(favorito.getId());
        dto.setProductoId(favorito.getProductoId());
        dto.setNota(favorito.getNota());
        dto.setFechaAgregado(favorito.getFechaAgregado());

        return dto;
    }
}