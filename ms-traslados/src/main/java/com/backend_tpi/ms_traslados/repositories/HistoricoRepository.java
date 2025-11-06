package com.backend_tpi.ms_traslados.repositories;

import com.backend_tpi.ms_traslados.models.HistoricoEstadoTraslado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoRepository extends JpaRepository<HistoricoEstadoTraslado, Long> {
}
