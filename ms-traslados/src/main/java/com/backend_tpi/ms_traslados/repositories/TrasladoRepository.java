package com.backend_tpi.ms_traslados.repositories;

import com.backend_tpi.ms_traslados.models.Traslado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrasladoRepository extends JpaRepository<Traslado, Long> {
}
