package com.backend_tpi.ms_rutas.mappers;

import com.backend_tpi.ms_rutas.dtos.responses.TramoDTO;
import com.backend_tpi.ms_rutas.dtos.responses.TramoDetalleDTO;
import com.backend_tpi.ms_rutas.models.Tramo;


public class TramoMapper {

    public static TramoDTO toDTO(Tramo tramo) {
        TramoDTO dto = new TramoDTO();
        dto.setIdTramo(tramo.getIdTramo());
        dto.setDireccionOrigen(tramo.getDireccionOrigen());
        dto.setDireccionDestino(tramo.getDireccionDestino());
        dto.setFechaInicioEstimada(tramo.getFechaHoraInicioEstimada());
        dto.setFechaFinEstimada(tramo.getFechaHoraFinEstimada());
        dto.setFechaInicioReal(tramo.getFechaHoraInicioReal());
        dto.setFechaFinReal(tramo.getFechaHoraFinReal());
        dto.setEstadoTramo(tramo.getEstadoTramo().name());
        dto.setTipoTramo(tramo.getTipoTramo().name());
        dto.setPatenteCamion(tramo.getPatenteCamion());
        return dto;
    }

    public static TramoDetalleDTO toDetalleDTO(Tramo tramo) {
        TramoDetalleDTO dto = new TramoDetalleDTO();
        dto.setIdTramo(tramo.getIdTramo());
        dto.setDireccionOrigen(tramo.getDireccionOrigen());
        dto.setDireccionDestino(tramo.getDireccionDestino());
        dto.setFechaInicioEstimada(tramo.getFechaHoraInicioEstimada());
        dto.setFechaFinEstimada(tramo.getFechaHoraFinEstimada());
        dto.setFechaInicioReal(tramo.getFechaHoraInicioReal());
        dto.setFechaFinReal(tramo.getFechaHoraFinReal());
        dto.setCostoEstimado(tramo.getCostoEstimado());
        dto.setCostoReal(tramo.getCostoReal());
        dto.setEstadoTramo(tramo.getEstadoTramo().name());
        dto.setTipoTramo(tramo.getTipoTramo().name());
        dto.setPatenteCamion(tramo.getPatenteCamion());
        return dto;
    }
}
