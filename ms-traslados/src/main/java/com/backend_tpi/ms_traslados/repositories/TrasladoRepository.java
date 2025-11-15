package com.backend_tpi.ms_traslados.repositories;

import com.backend_tpi.ms_traslados.dtos.responses.TrasladoResumenDtoRes;
import com.backend_tpi.ms_traslados.models.Traslado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrasladoRepository extends JpaRepository<Traslado, Long> {

  Optional<Traslado> findByIdTraslado(Long idTraslado);

  List<Traslado> findByClienteNroDocumento(Long nroDocumento);
}
