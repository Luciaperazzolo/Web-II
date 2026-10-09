package com.ejemplo.tp1_APIREST.Service;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ejemplo.tp1_APIREST.Model.Lista;
import com.ejemplo.tp1_APIREST.Repository.ListaRepository;
import com.ejemplo.tp1_APIREST.DTO.FavoritoSalidaDTO;
import com.ejemplo.tp1_APIREST.Exception.ConflictoException;
import com.ejemplo.tp1_APIREST.Exception.RecursoNoEncontradoException;
import com.ejemplo.tp1_APIREST.Repository.FavoritoRepository;
import com.ejemplo.tp1_APIREST.Model.Favorito;

@Service
public class ListaService {
    private final ListaRepository listaRepository;
    private final FavoritoRepository favoritoRepository;

    public ListaService(ListaRepository listaRepository, FavoritoRepository favoritoRepository) {
        this.listaRepository = listaRepository;
        this.favoritoRepository = favoritoRepository;
    }

    public Lista crear(String nombre) {
        Lista lista = new Lista();
        lista.setNombre(nombre);
        return listaRepository.guardar(lista);
    }

    public List<Lista> buscarTodos() {
        return listaRepository.buscarTodos();
    }

    public Lista buscarPorId(Long id) {
        return listaRepository.buscarPorId(id).orElseThrow(() -> new RecursoNoEncontradoException("No existe una lista con el id " + id));
    }

    public List<FavoritoSalidaDTO> buscarFavoritos(Long listaId) {
        // Primero verificamos que la lista exista
        buscarPorId(listaId);

        // Buscamos los favoritos asociados a esa lista
        return favoritoRepository.buscarPorListaId(listaId).stream().map(this::convertirFavoritoASalida).toList();
    }

    public void eliminar(Long id) {
        //Comprobar que la lista existe
        Lista lista = listaRepository.buscarPorId(id).orElseThrow(() ->new RecursoNoEncontradoException("No existe una lista con el id " + id));

        //Buscar los favoritos asociados a esa lista
        List<Favorito> favoritos =favoritoRepository.buscarPorListaId(lista.getId());

        //Si tiene favoritos, impedir la eliminación
        if (!favoritos.isEmpty()) {
            throw new ConflictoException("No se puede eliminar una lista que tiene favoritos asociados");
        }

        //Si no tiene favoritos, eliminar la lista
        listaRepository.eliminarPorId(id);
    }

    private FavoritoSalidaDTO convertirFavoritoASalida(Favorito favorito) {
        FavoritoSalidaDTO dto = new FavoritoSalidaDTO();

        dto.setId(favorito.getId());
        dto.setProductoId(favorito.getProductoId());
        dto.setNota(favorito.getNota());
        dto.setFechaAgregado(favorito.getFechaAgregado());
        dto.setListaId(favorito.getListaId());

        return dto;
    }

    @Transactional
    public void moverFavoritos(Long origenId, Long destinoId) {

        //Comprobar que la lista de origen existe
        Lista origen = listaRepository.buscarPorId(origenId).orElseThrow(() ->new RecursoNoEncontradoException("No existe la lista de origen con el id " + origenId));

        //Comprobar que la lista de destino existe
        listaRepository.buscarPorId(destinoId).orElseThrow(() ->new RecursoNoEncontradoException("No existe la lista de destino con el id " + destinoId));

        //Impedir mover una lista hacia sí misma
        if (origenId.equals(destinoId)) {
            throw new ConflictoException("La lista de origen y destino no pueden ser la misma");
        }

        //Obtener todos los favoritos de la lista de origen
        List<Favorito> favoritos =favoritoRepository.buscarPorListaId(origenId);

        //Cambiar cada favorito a la lista de destino
        for (Favorito favorito : favoritos) {
            favorito.setListaId(destinoId);
            favoritoRepository.actualizar(favorito);
        }

        //Eliminar la lista de origen una vez que quedó vacía
        listaRepository.eliminarPorId(origen.getId());
    }
}
