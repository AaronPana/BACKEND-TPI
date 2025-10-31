package com.backend_tpi.ms_rutas.constants.converters;

import com.backend_tpi.ms_rutas.constants.EstadoTramo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EstadoTramoConverter implements AttributeConverter<EstadoTramo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EstadoTramo estadoTramo) {
        if (estadoTramo == null) {
            return null;
        }
        return estadoTramo.getId();
    }

    @Override
    public EstadoTramo convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }
        return EstadoTramo.fromId(id);
    }
}

