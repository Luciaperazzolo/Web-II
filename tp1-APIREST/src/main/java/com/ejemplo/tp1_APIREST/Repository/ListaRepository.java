package com.ejemplo.tp1_APIREST.Repository;

import java.util.List;
import java.util.Optional;

import com.ejemplo.tp1_APIREST.Model.Lista;

public interface ListaRepository {
    Lista guardar(Lista lista);

    List<Lista> buscarTodos();

    Optional<Lista> buscarPorId(Long id);

    boolean eliminarPorId(Long id);
}
