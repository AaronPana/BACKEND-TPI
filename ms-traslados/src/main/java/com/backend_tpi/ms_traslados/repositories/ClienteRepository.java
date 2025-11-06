package com.backend_tpi.ms_traslados.repositories;

import com.backend_tpi.ms_traslados.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
