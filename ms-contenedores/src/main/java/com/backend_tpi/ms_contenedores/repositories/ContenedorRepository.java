package com.backend_tpi.ms_contenedores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend_tpi.ms_contenedores.models.Contenedores;

@Repository
public interface ContenedorRepository extends JpaRepository<Contenedores, Long> {

}