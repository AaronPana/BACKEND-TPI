package com.backend_tpi.ms_camiones.repositories;

import com.backend_tpi.ms_camiones.models.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

    List<Ciudad> findByProvincia_IdProvincia(Long idProvincia);
}
