package com.backend_tpi.ms_contenedores.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend_tpi.ms_contenedores.models.Estadias;

@Repository
public interface EstadiaRepository extends JpaRepository<Estadias, Long> {

}

