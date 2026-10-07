package com.ejemplo.tp1_APIREST.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ejemplo.tp1_APIREST.Entity.ListaEntity;
import com.ejemplo.tp1_APIREST.Model.Lista;

@Repository
public class ListaRepositoryAdapter implements ListaRepository{
    private final ListaJpaRepository listaJpaRepository;

    public ListaRepositoryAdapter(ListaJpaRepository listaJpaRepository) {
        this.listaJpaRepository = listaJpaRepository;
    }

    @Override
    public Lista guardar(Lista lista) {

        ListaEntity entity = convertirAEntity(lista);

        ListaEntity guardada = listaJpaRepository.save(entity);

        lista.setId(guardada.getId());

        return lista;
    }

    @Override
    public List<Lista> buscarTodos() {

        return listaJpaRepository.findAll()
                .stream()
                .map(this::convertirADominio)
                .toList();
    }

    @Override
    public Optional<Lista> buscarPorId(Long id) {

        return listaJpaRepository.findById(id)
                .map(this::convertirADominio);
    }

    @Override
    public boolean eliminarPorId(Long id) {

        if (!listaJpaRepository.existsById(id)) {
            return false;
        }

        listaJpaRepository.deleteById(id);

        return true;
    }

    private ListaEntity convertirAEntity(Lista lista) {

        ListaEntity entity = new ListaEntity();

        entity.setNombre(lista.getNombre());

        return entity;
    }

    private Lista convertirADominio(ListaEntity entity) {

        Lista lista = new Lista();

        lista.setId(entity.getId());
        lista.setNombre(entity.getNombre());

        return lista;
    }
}
