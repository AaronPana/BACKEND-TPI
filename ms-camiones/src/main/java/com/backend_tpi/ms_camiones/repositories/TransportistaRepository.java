package com.backend_tpi.ms_camiones.repositories;

import com.backend_tpi.ms_camiones.models.Transportista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportistaRepository extends JpaRepository<Transportista, Integer> {

    // Buscar transportistas por ciudad (id)
    List<Transportista> findByCiudad_IdCiudad(Integer idCiudad);

    // Búsqueda simple por nombre/apellido (case-insensitive)
    List<Transportista> findByNombreIgnoreCase(String nombre);
    List<Transportista> findByApellidoIgnoreCase(String apellido);
}
