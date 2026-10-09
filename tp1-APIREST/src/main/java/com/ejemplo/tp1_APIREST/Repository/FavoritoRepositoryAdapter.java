package com.ejemplo.tp1_APIREST.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ejemplo.tp1_APIREST.Entity.FavoritoEntity;
import com.ejemplo.tp1_APIREST.Entity.ListaEntity;
import com.ejemplo.tp1_APIREST.Model.Favorito;

@Repository
public class FavoritoRepositoryAdapter implements FavoritoRepository{
    private final FavoritoJpaRepository favoritoJpaRepository;

    public FavoritoRepositoryAdapter(FavoritoJpaRepository favoritoJpaRepository) {
        this.favoritoJpaRepository = favoritoJpaRepository;
    }
    
    @Override
    public Favorito guardar(Favorito favorito) {
        FavoritoEntity entity = convertirAEntity(favorito);
        FavoritoEntity guardado = favoritoJpaRepository.save(entity);
        favorito.setId(guardado.getId());

        return favorito;
    }

    @Override
    public List<Favorito> buscarTodos() {
        return favoritoJpaRepository.findAll().stream().map(this::convertirADominio).toList();
    }

    @Override
    public Optional<Favorito> buscarPorId(Long id) {
        return favoritoJpaRepository.findById(id).map(this::convertirADominio);
    }

    @Override
    public List<Favorito> buscarPorListaId(Long listaId) {
        return favoritoJpaRepository.findByListaId(listaId).stream().map(this::convertirADominio).toList();
    }

    @Override
    public boolean eliminarPorId(Long id) {

        if (!favoritoJpaRepository.existsById(id)) {
            return false;
        }
        favoritoJpaRepository.deleteById(id);
        return true;
    }

    private FavoritoEntity convertirAEntity(Favorito favorito) {
        FavoritoEntity entity = new FavoritoEntity();

        entity.setProductoId(favorito.getProductoId());
        entity.setNota(favorito.getNota());
        entity.setFechaAgregado(favorito.getFechaAgregado());

        if (favorito.getListaId() != null) {
            ListaEntity lista = new ListaEntity();
            lista.setId(favorito.getListaId());
            entity.setLista(lista);
        }
        return entity;
    }

    private Favorito convertirADominio(FavoritoEntity entity) {
        Favorito favorito = new Favorito();

        favorito.setId(entity.getId());
        favorito.setProductoId(entity.getProductoId());
        favorito.setNota(entity.getNota());
        favorito.setFechaAgregado(entity.getFechaAgregado());

        if (entity.getLista() != null) {
            favorito.setListaId(entity.getLista().getId());
        }
        return favorito;
    }

    @Override
    public Favorito actualizar(Favorito favorito) {
        FavoritoEntity entity = convertirAEntity(favorito);

        //Conservamos el ID para actualizar el registro existente.
        entity.setId(favorito.getId());

        FavoritoEntity actualizado = favoritoJpaRepository.save(entity);
        favorito.setId(actualizado.getId());

        return favorito;
    }
}
