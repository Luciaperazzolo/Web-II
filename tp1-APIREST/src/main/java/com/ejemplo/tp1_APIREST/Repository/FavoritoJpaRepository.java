package com.ejemplo.tp1_APIREST.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ejemplo.tp1_APIREST.Entity.FavoritoEntity;

public interface FavoritoJpaRepository extends JpaRepository<FavoritoEntity, Long> {
    List<FavoritoEntity> findByListaId(Long listaId);
}
