package com.backend_tpi.ms_rutas.repositories;

import com.backend_tpi.ms_rutas.models.Tramo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TramoRepository extends JpaRepository<Tramo, Long> {

    List<Tramo> findByLegajoTransportista(Long legajoTransportista);
    List<Tramo> findByIdTrasladoOrderByIdTramoAsc(Long idTraslado);
}
