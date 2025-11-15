package com.backend_tpi.ms_camiones.repositories;

import com.backend_tpi.ms_camiones.models.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
}
