package com.backend_tpi.ms_traslados.services;

import com.backend_tpi.ms_traslados.dtos.responses.ClienteDtoRes;
import com.backend_tpi.ms_traslados.dtos.responses.FullClienteDtoRes;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoSinClienteDtoRes;
import com.backend_tpi.ms_traslados.exceptions.BaseException;
import com.backend_tpi.ms_traslados.external.clients.CamionesApiClient;
import com.backend_tpi.ms_traslados.external.dtos.responses.CiudadProvinciaDtoRes;
import com.backend_tpi.ms_traslados.mappers.ClienteMapper;
import com.backend_tpi.ms_traslados.models.Cliente;
import com.backend_tpi.ms_traslados.repositories.ClienteRepository;
import com.backend_tpi.ms_traslados.repositories.TrasladoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

  private final CamionesApiClient camionesApiClient;
  private final TrasladoRepository trasladoRepository;
  private final ClienteRepository clienteRepository;
  private final ClienteMapper clienteMapper;

  public List<ClienteDtoRes> getAll() {
    List<Cliente> clientes = this.clienteRepository.findAll();
    return clientes.stream().map((Cliente cliente) -> {
      CiudadProvinciaDtoRes ciudadProvinciaDtoRes = this.camionesApiClient.getCiudadProvincia(cliente.getIdCiudad());
      return this.clienteMapper.clienteToDtoRes(cliente, ciudadProvinciaDtoRes.getCiudadProvincia());
    }).toList();
  }

  public FullClienteDtoRes getById(Long nroDocumento) {
    Cliente cliente = this.clienteRepository.findById(nroDocumento)
        .orElseThrow(() -> BaseException.notFoundById("Cliente", nroDocumento));

    CiudadProvinciaDtoRes ciudadProvinciaDtoRes = this.camionesApiClient.getCiudadProvincia(cliente.getIdCiudad());

    List<TrasladoSinClienteDtoRes> traslados = this.trasladoRepository.findByClienteNroDocumento(cliente.getNroDocumento());

    return this.clienteMapper.fullClienteToDtoRes(cliente, ciudadProvinciaDtoRes.getCiudadProvincia(), traslados);
  }
}
