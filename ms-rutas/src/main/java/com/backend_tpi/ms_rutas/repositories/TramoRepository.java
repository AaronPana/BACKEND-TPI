package com.backend_tpi.ms_rutas.repositories;

import com.backend_tpi.ms_rutas.models.Tramo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TramoRepository extends JpaRepository<Tramo, Long> {

}
