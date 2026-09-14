package com.ejemplo.tp1_APIREST.Repository;

import com.ejemplo.tp1_APIREST.Model.Favorito; 
import java.util.List; 
import java.util.Optional;

public interface FavoritoRepository {
    Favorito guardar(Favorito favorito); 
    List<Favorito> buscarTodos(); 
    Optional<Favorito> buscarPorId(Long id); 
    boolean eliminarPorId(Long id);
    
} 
