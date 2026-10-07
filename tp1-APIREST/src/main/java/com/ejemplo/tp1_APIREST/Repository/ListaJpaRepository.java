package com.ejemplo.tp1_APIREST.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ejemplo.tp1_APIREST.Entity.ListaEntity;

public interface ListaJpaRepository extends JpaRepository<ListaEntity, Long>{
}
