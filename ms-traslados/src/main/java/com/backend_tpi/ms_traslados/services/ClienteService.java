package com.backend_tpi.ms_traslados.services;

import com.backend_tpi.ms_traslados.dtos.responses.ClienteDtoRes;
import com.backend_tpi.ms_traslados.mappers.ClienteMapper;
import com.backend_tpi.ms_traslados.models.Cliente;
import com.backend_tpi.ms_traslados.repositories.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

  private final ClienteRepository clienteRepository;
  private final ClienteMapper clienteMapper;

  public List<ClienteDtoRes> getAll() {
    List<Cliente> clientes = this.clienteRepository.findAll();
    return clientes.stream().map(this.clienteMapper::clienteToDtoRes).toList();
  }
}
