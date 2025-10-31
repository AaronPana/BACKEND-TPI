package com.backend_tpi.ms_rutas.constants.converters;

import com.backend_tpi.ms_rutas.constants.TipoTramo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoTramoConverter implements AttributeConverter<TipoTramo, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoTramo tipoTramo) {
        if (tipoTramo == null) {
            return null;
        }
        return tipoTramo.getId();
    }

    @Override
    public TipoTramo convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }
        return TipoTramo.fromId(id);
    }
}

