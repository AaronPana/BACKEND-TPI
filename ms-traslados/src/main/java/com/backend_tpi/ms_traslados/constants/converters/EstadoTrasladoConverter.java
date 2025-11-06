package com.backend_tpi.ms_traslados.constants.converters;

import com.backend_tpi.ms_traslados.constants.EstadoTraslado;
import jakarta.persistence.AttributeConverter;

public class EstadoTrasladoConverter implements AttributeConverter<EstadoTraslado, Integer> {

  @Override
  public Integer convertToDatabaseColumn(EstadoTraslado estadoTraslado) {
    if (estadoTraslado == null) {
      return null;
    }
    return estadoTraslado.getId();
  }

  @Override
  public EstadoTraslado convertToEntityAttribute(Integer idEstadoTraslado) {
    if (idEstadoTraslado == null) {
      return null;
    }
    return EstadoTraslado.fromId(idEstadoTraslado);
  }
}
