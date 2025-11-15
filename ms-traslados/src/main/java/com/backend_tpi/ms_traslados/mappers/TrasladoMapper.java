package com.backend_tpi.ms_traslados.mappers;

import com.backend_tpi.ms_traslados.dtos.requests.TrasladoPostDtoReq;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoDetalleDtoRes;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoMetricasDtoRes;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoResumenDtoRes;
import com.backend_tpi.ms_traslados.external.dtos.responses.ContenedorDtoRes;
import com.backend_tpi.ms_traslados.models.Cliente;
import com.backend_tpi.ms_traslados.models.Traslado;
import org.springframework.stereotype.Component;

import java.time.Duration;
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

  public TrasladoResumenDtoRes trasladoToTrasladoResumenDtoRes(Traslado traslado) {
    TrasladoResumenDtoRes trasladoResumenDtoRes = new TrasladoResumenDtoRes();
    trasladoResumenDtoRes.setIdTraslado(traslado.getIdTraslado());
    trasladoResumenDtoRes.setFechaInicioTraslado(traslado.getFechaInicioTraslado());
    trasladoResumenDtoRes.setFechaFinTraslado(traslado.getFechaFinTraslado());
    trasladoResumenDtoRes.setDireccionOrigen(traslado.getDireccionOrigen());
    trasladoResumenDtoRes.setDireccionDestino(traslado.getDireccionDestino());
    trasladoResumenDtoRes.setIdCiudadOrigen(traslado.getIdCiudadOrigen());
    trasladoResumenDtoRes.setIdCiudadDestino(traslado.getIdCiudadDestino());
    return trasladoResumenDtoRes;
  }

  public TrasladoDetalleDtoRes trasladoToTrasladoDetalleDtoRes(Traslado traslado, ContenedorDtoRes contenedorDtoRes) {
    TrasladoDetalleDtoRes trasladoDetalleDtoRes = new TrasladoDetalleDtoRes();
    trasladoDetalleDtoRes.setIdTraslado(traslado.getIdTraslado());
    trasladoDetalleDtoRes.setFechaInicioTraslado(traslado.getFechaInicioTraslado());
    trasladoDetalleDtoRes.setFechaFinTraslado(traslado.getFechaFinTraslado());
    trasladoDetalleDtoRes.setCostoEstimado(traslado.getCostoEstimado());
    trasladoDetalleDtoRes.setCostoReal(traslado.getCostoReal());
    trasladoDetalleDtoRes.setTiempoEstimado(
        this.parseDurationToString(traslado.getTiempoEstimado())
    );
    trasladoDetalleDtoRes.setTiempoReal(
        this.parseDurationToString(traslado.getTiempoReal())
    );
    trasladoDetalleDtoRes.setDireccionOrigen(traslado.getDireccionOrigen());
    trasladoDetalleDtoRes.setDireccionDestino(traslado.getDireccionDestino());
    trasladoDetalleDtoRes.setIdCiudadOrigen(traslado.getIdCiudadOrigen());
    trasladoDetalleDtoRes.setIdCiudadDestino(traslado.getIdCiudadDestino());
    trasladoDetalleDtoRes.setContenedor(contenedorDtoRes);
    return trasladoDetalleDtoRes;
  }

  public TrasladoMetricasDtoRes trasladoToTrasladoMetricasDtoRes(Traslado traslado) {
    TrasladoMetricasDtoRes trasladoMetricasDtoRes = new TrasladoMetricasDtoRes();
    trasladoMetricasDtoRes.setCostoEstimado(traslado.getCostoEstimado());
    trasladoMetricasDtoRes.setCostoReal(traslado.getCostoReal());
    trasladoMetricasDtoRes.setTiempoEstimado(
        this.parseDurationToString(traslado.getTiempoEstimado())
    );
    trasladoMetricasDtoRes.setTiempoReal(
        this.parseDurationToString(traslado.getTiempoReal())
    );
    return  trasladoMetricasDtoRes;
  }

  public Duration parseStringToDuration(String duracionStr) {
    if (duracionStr == null) return null;

    // Formato "dd HH:mm:ss"
    String[] partes = duracionStr.split(" ");
    long dias = Long.parseLong(partes[0]);

    String[] hms = partes[1].split(":");
    long horas = Long.parseLong(hms[0]);
    long minutos = Long.parseLong(hms[1]);
    long segundos = Long.parseLong(hms[2]);

    return Duration.ofDays(dias)
        .plusHours(horas)
        .plusMinutes(minutos)
        .plusSeconds(segundos);
  }

  public String parseDurationToString(Duration duracion) {
    if (duracion == null) return null;

    long dias = duracion.toDays();
    duracion = duracion.minusDays(dias);
    long horas = duracion.toHours();
    duracion = duracion.minusHours(horas);
    long minutos = duracion.toMinutes();
    duracion = duracion.minusMinutes(minutos);
    long segundos = duracion.getSeconds();
    return String.format("%02d %02d:%02d:%02d", dias, horas, minutos, segundos);
  }
}
