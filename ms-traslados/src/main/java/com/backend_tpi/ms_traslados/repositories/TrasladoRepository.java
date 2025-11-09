package com.backend_tpi.ms_traslados.repositories;

import com.backend_tpi.ms_traslados.dtos.responses.TrasladoSinClienteDtoRes;
import com.backend_tpi.ms_traslados.models.Traslado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrasladoRepository extends JpaRepository<Traslado, Long> {

  List<TrasladoSinClienteDtoRes> findByClienteNroDocumento(Long nroDocumento);
}
