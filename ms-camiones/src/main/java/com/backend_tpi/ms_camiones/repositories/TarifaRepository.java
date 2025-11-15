package com.backend_tpi.ms_camiones.repositories;

import com.backend_tpi.ms_camiones.models.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Integer> {
}
