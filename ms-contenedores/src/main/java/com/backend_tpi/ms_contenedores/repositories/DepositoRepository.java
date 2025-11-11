package com.backend_tpi.ms_contenedores.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend_tpi.ms_contenedores.models.Depositos;

@Repository
public interface DepositoRepository extends JpaRepository<Depositos, Long> {

}
