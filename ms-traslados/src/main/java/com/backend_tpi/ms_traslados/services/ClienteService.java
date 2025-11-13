package com.backend_tpi.ms_traslados.services;

import com.backend_tpi.ms_traslados.dtos.requests.ClientePostDtoReq;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

  private final CamionesApiClient camionesApiClient;
  private final TrasladoRepository trasladoRepository;
  private final ClienteRepository clienteRepository;
  private final ClienteMapper clienteMapper;

  public ClienteDtoRes create(ClientePostDtoReq clientePostDtoReq) {
    Optional<Cliente> clienteFinded = this.clienteRepository.findById(clientePostDtoReq.getNroDocumento());
    if (clienteFinded.isPresent()) {
      throw BaseException.alreadyExists("Cliente", "nroDocumento", clientePostDtoReq.getNroDocumento());
    }

    Cliente cliente = this.clienteMapper.postDtoReqToCliente(clientePostDtoReq);
    CiudadProvinciaDtoRes ciudadProvinciaDtoRes = this.camionesApiClient.getCiudadProvincia(cliente.getIdCiudad());

    this.clienteRepository.save(cliente);
    return this.clienteMapper.clienteToDtoRes(cliente, ciudadProvinciaDtoRes.getCiudadProvincia());
  }

  public List<ClienteDtoRes> getAll() {
    List<Cliente> clientes = this.clienteRepository.findAll();
    return clientes.stream().map((Cliente cliente) -> {
      CiudadProvinciaDtoRes ciudadProvinciaDtoRes = this.camionesApiClient.getCiudadProvincia(cliente.getIdCiudad());
      return this.clienteMapper.clienteToDtoRes(cliente, ciudadProvinciaDtoRes.getCiudadProvincia());
    }).toList();
  }

  public FullClienteDtoRes getById(Long nroDocumento) {
    return this.clienteRepository.findById(nroDocumento)
        .map(cliente -> {
          CiudadProvinciaDtoRes ciudadProvincia = this.camionesApiClient.getCiudadProvincia(cliente.getIdCiudad());
          List<TrasladoSinClienteDtoRes> traslados = this.trasladoRepository.findByClienteNroDocumento(nroDocumento);
          return this.clienteMapper.fullClienteToDtoRes(cliente, ciudadProvincia.getCiudadProvincia(), traslados);
        })
        .orElseThrow(() -> BaseException.notFoundById("Cliente", nroDocumento));
  }

  public void delete(Long nroDocumento) {
    Cliente cliente = this.clienteRepository.findById(nroDocumento)
        .orElseThrow(() -> BaseException.notFoundById("Cliente", nroDocumento));

    if (!cliente.getTraslados().isEmpty()) {
      throw BaseException.businessError("El cliente contiene traslados asociados");
    }

    this.clienteRepository.delete(cliente);
  }
}
