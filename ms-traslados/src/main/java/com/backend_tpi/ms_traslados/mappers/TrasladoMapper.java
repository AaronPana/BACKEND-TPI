package com.backend_tpi.ms_traslados.mappers;

import com.backend_tpi.ms_traslados.dtos.requests.TrasladoPostDtoReq;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoSinClienteDtoRes;
import com.backend_tpi.ms_traslados.models.Cliente;
import com.backend_tpi.ms_traslados.models.Traslado;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TrasladoMapper {

  public Traslado postDtoReqToTraslado(TrasladoPostDtoReq trasladoPostDtoReq, Cliente cliente, Long idContenedor) {
    Traslado traslado = new Traslado();
    traslado.setDireccionOrigen(trasladoPostDtoReq.getDireccionOrigen());
    traslado.setDireccionDestino(trasladoPostDtoReq.getDireccionDestino());
    traslado.setIdCiudadOrigen(trasladoPostDtoReq.getIdCiudadOrigen());
    traslado.setIdCiudadDestino(trasladoPostDtoReq.getIdCiudadDestino());
    traslado.setIdContenedor(idContenedor);
    traslado.setCliente(cliente);
    return traslado;
  }

  public TrasladoSinClienteDtoRes trasladoToTrasladoSinClienteDtoRes(Traslado traslado) {
    return new TrasladoSinClienteDtoRes() {
      @Override
      public Long getIdTraslado() {
        return traslado.getIdTraslado();
      }

      @Override
      public LocalDateTime getFechaInicioTraslado() {
        return traslado.getFechaInicioTraslado();
      }

      @Override
      public LocalDateTime getFechaFinTraslado() {
        return traslado.getFechaFinTraslado();
      }

      @Override
      public Double getCostoEstimado() {
        return traslado.getCostoEstimado();
      }

      @Override
      public Double getCostoReal() {
        return traslado.getCostoReal();
      }

      @Override
      public Integer getTiempoEstimado() {
        return traslado.getTiempoEstimado();
      }

      @Override
      public Integer getTiempoReal() {
        return traslado.getTiempoReal();
      }

      @Override
      public String getDireccionOrigen() {
        return traslado.getDireccionOrigen();
      }

      @Override
      public String getDireccionDestino() {
        return traslado.getDireccionDestino();
      }

      @Override
      public Long getIdCiudadOrigen() {
        return traslado.getIdCiudadOrigen();
      }

      @Override
      public Long getIdCiudadDestino() {
        return traslado.getIdCiudadDestino();
      }

      @Override
      public Long getIdContenedor() {
        return traslado.getIdContenedor();
      }
    };
  }
}
