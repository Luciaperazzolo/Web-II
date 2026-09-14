package com.ejemplo.tp1_APIREST.Repository;

import com.ejemplo.tp1_APIREST.Model.Favorito; 
import java.util.ArrayList; 
import java.util.List; 
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class FavoritoRepositoryMemoria implements FavoritoRepository {
    private final List<Favorito> favoritos = new ArrayList<>();

    @Override 
    public Favorito guardar(Favorito favorito) { 
        favoritos.add(favorito); 
        return favorito; 
    }

    @Override 
    public List<Favorito> buscarTodos() { 
        return favoritos; 
    }

    @Override 
    public Optional<Favorito> buscarPorId(Long id) { 
        return favoritos.stream() 
            .filter(favorito -> favorito.getId().equals(id)) 
            .findFirst(); 
    }

    @Override public boolean eliminarPorId(Long id) { 
        return favoritos.removeIf(favorito -> favorito.getId().equals(id)); 
    }
}
