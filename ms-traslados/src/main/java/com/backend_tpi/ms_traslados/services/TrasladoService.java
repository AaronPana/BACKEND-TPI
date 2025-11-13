package com.backend_tpi.ms_traslados.services;

import com.backend_tpi.ms_traslados.dtos.requests.TrasladoPostDtoReq;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoSinClienteDtoRes;
import com.backend_tpi.ms_traslados.exceptions.BaseException;
import com.backend_tpi.ms_traslados.external.clients.ContenedoresApiClient;
import com.backend_tpi.ms_traslados.mappers.TrasladoMapper;
import com.backend_tpi.ms_traslados.models.Cliente;
import com.backend_tpi.ms_traslados.models.Traslado;
import com.backend_tpi.ms_traslados.repositories.ClienteRepository;
import com.backend_tpi.ms_traslados.repositories.TrasladoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TrasladoService {

  private final ContenedoresApiClient contenedoresApiClient;
  private final TrasladoRepository trasladoRepository;
  private final ClienteRepository clienteRepository;
  private final TrasladoMapper trasladoMapper;

  public TrasladoSinClienteDtoRes create(TrasladoPostDtoReq trasladoPostDtoReq) {
    Cliente cliente = this.clienteRepository.findById(trasladoPostDtoReq.getNroDocumento())
        .orElseThrow(() -> BaseException.notFoundById("Cliente", trasladoPostDtoReq.getNroDocumento()));

    // NO SE ESTAN VALIDANDO LOS IDS DE CIDUDAES
    // NO SE OBTIENE REALMENTE UN ID_CONTENEDOR
    Long idContenedor = (Long) this.contenedoresApiClient.postContenedor();

    Traslado traslado = this.trasladoMapper.postDtoReqToTraslado(trasladoPostDtoReq, cliente, idContenedor);

    this.trasladoRepository.save(traslado);

    return this.trasladoMapper.trasladoToTrasladoSinClienteDtoRes(traslado);
  }

  public TrasladoSinClienteDtoRes getById(Long idTraslado) {
    return this.trasladoRepository.findByIdTraslado(idTraslado)
        .orElseThrow(() -> BaseException.notFoundById("Traslado", idTraslado));
  }

  public void delete(Long idTraslado) {
    Traslado traslado = this.trasladoRepository.findById(idTraslado)
        .orElseThrow(() -> BaseException.notFoundById("Traslado", idTraslado));

    this.trasladoRepository.delete(traslado);
  }
}
