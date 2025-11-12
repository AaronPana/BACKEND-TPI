package com.backend_tpi.ms_camiones.repositories;

import com.backend_tpi.ms_camiones.models.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CamionRepository extends JpaRepository<Camion, String> {

    // Lista por disponibilidad: 'S' o 'N'
    List<Camion> findByEstaDisponible(String estaDisponible);

    // Trae el primero disponible (determinístico por patente)
    Optional<Camion> findFirstByEstaDisponibleOrderByPatenteAsc(String estaDisponible);

    // Busca el primer camión disponible que tenga capacidadPeso >= x y capacidadVolumen >= y
    Optional<Camion> findFirstByEstaDisponibleAndCapacidadPesoGreaterThanEqualAndCapacidadVolumenGreaterThanEqualOrderByPatenteAsc(
            String estaDisponible,
            double capacidadPeso,
            double capacidadVolumen
    );
}
