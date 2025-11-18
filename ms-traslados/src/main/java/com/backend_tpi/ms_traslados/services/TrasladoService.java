package com.backend_tpi.ms_traslados.services;

import com.backend_tpi.ms_traslados.dtos.requests.TrasladoPatchDtoReq;
import com.backend_tpi.ms_traslados.dtos.requests.TrasladoPostDtoReq;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoDetalleDtoRes;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoMetricasDtoRes;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoResumenDtoRes;
import com.backend_tpi.ms_traslados.exceptions.BaseException;
import com.backend_tpi.ms_traslados.external.clients.ContenedoresApiClient;
import com.backend_tpi.ms_traslados.external.dtos.requests.PesoVolumenDtoReq;
import com.backend_tpi.ms_traslados.external.dtos.responses.ContenedorDtoRes;
import com.backend_tpi.ms_traslados.mappers.TrasladoMapper;
import com.backend_tpi.ms_traslados.models.Cliente;
import com.backend_tpi.ms_traslados.models.Traslado;
import com.backend_tpi.ms_traslados.repositories.ClienteRepository;
import com.backend_tpi.ms_traslados.repositories.TrasladoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
@RequiredArgsConstructor
public class TrasladoService {

  private final ContenedoresApiClient contenedoresApiClient;
  private final TrasladoRepository trasladoRepository;
  private final ClienteRepository clienteRepository;
  private final TrasladoMapper trasladoMapper;

  public TrasladoResumenDtoRes create(TrasladoPostDtoReq trasladoPostDtoReq) {
    Cliente cliente = this.clienteRepository.findById(trasladoPostDtoReq.getNroDocumento())
        .orElseThrow(() -> BaseException.notFoundById("Cliente", trasladoPostDtoReq.getNroDocumento()));

    // NO SE ESTAN VALIDANDO LOS IDS DE CIDUDAES

    PesoVolumenDtoReq pesoVolumenDtoReq = new PesoVolumenDtoReq(
        trasladoPostDtoReq.getPeso(),
        trasladoPostDtoReq.getVolumen()
    );
    ContenedorDtoRes contenedorDtoRes = this.contenedoresApiClient.postContenedor(pesoVolumenDtoReq);

    Traslado traslado = this.trasladoMapper.postDtoReqToTraslado(trasladoPostDtoReq, cliente, contenedorDtoRes.getIdContenedor());

    this.trasladoRepository.save(traslado);

    return this.trasladoMapper.trasladoToTrasladoResumenDtoRes(traslado);
  }

  public TrasladoDetalleDtoRes getById(Long idTraslado) {
    Traslado traslado = this.trasladoRepository.findByIdTraslado(idTraslado)
        .orElseThrow(() -> BaseException.notFoundById("Traslado", idTraslado));

    ContenedorDtoRes contenedorDtoRes = this.contenedoresApiClient.getContenedorById(traslado.getIdContenedor());

    return this.trasladoMapper.trasladoToTrasladoDetalleDtoRes(traslado,contenedorDtoRes);
  }

  public TrasladoMetricasDtoRes getMetricasById(Long idTraslado) {
    Traslado traslado = this.trasladoRepository.findByIdTraslado(idTraslado)
        .orElseThrow(() -> BaseException.notFoundById("Traslado", idTraslado));

    return this.trasladoMapper.trasladoToTrasladoMetricasDtoRes(traslado);
  }

  public TrasladoDetalleDtoRes actualizarParcial(Long idTraslado, TrasladoPatchDtoReq trasladoPatchDtoReq) {
    Traslado traslado = this.trasladoRepository.findById(idTraslado)
        .orElseThrow(() -> BaseException.notFoundById("Traslado", idTraslado));

    ContenedorDtoRes contenedorDtoRes = this.contenedoresApiClient.getContenedorById(traslado.getIdContenedor());

    if (trasladoPatchDtoReq.getFechaInicioTraslado() != null) {
      if (traslado.getFechaFinTraslado() != null) {
        throw BaseException.badRequest("No se puede actualizar la fecha-hora inicio, el traslado ya finalizó");
      }

      traslado.setFechaInicioTraslado(trasladoPatchDtoReq.getFechaInicioTraslado());
    }

    if (trasladoPatchDtoReq.getFechaFinTraslado() != null) {
      if (traslado.getFechaInicioTraslado() == null) {
        throw BaseException.badRequest("No se puede actualizar la fecha-hora fin, el traslado no inició");
      }

      if (!trasladoPatchDtoReq.getFechaFinTraslado().isAfter(traslado.getFechaInicioTraslado())) {
        throw BaseException.badRequest(
            "No se puede actualizar la fecha-hora fin, esta debe ser posterior a la fecha-hora inicio"
        );
      }

      traslado.setFechaFinTraslado(trasladoPatchDtoReq.getFechaFinTraslado());
    }

    if (trasladoPatchDtoReq.getCostoEstimado() != null) {
      traslado.setCostoEstimado(trasladoPatchDtoReq.getCostoEstimado());
    }

    if (trasladoPatchDtoReq.getCostoReal() != null) {
      traslado.setCostoReal(trasladoPatchDtoReq.getCostoReal());
    }

    if (trasladoPatchDtoReq.getTiempoEstimado() != null) {
      Duration duracion = this.trasladoMapper.parseStringToDuration(trasladoPatchDtoReq.getTiempoEstimado());
      traslado.setTiempoEstimado(duracion);
    }

    if (trasladoPatchDtoReq.getTiempoReal() != null) {
      Duration duracion = this.trasladoMapper.parseStringToDuration(trasladoPatchDtoReq.getTiempoReal());
      traslado.setTiempoReal(duracion);
    }

    Traslado trasladoActualizado = this.trasladoRepository.save(traslado);

    return this.trasladoMapper.trasladoToTrasladoDetalleDtoRes(trasladoActualizado, contenedorDtoRes);
  }

  public void delete(Long idTraslado) {
    Traslado traslado = this.trasladoRepository.findById(idTraslado)
        .orElseThrow(() -> BaseException.notFoundById("Traslado", idTraslado));

    this.trasladoRepository.delete(traslado);
  }
}
