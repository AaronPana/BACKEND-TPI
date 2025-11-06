package com.backend_tpi.ms_contenedores.constants.converters;

import com.backend_tpi.ms_contenedores.constants.EstadoContenedor;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EstadoContenedorConverter implements AttributeConverter<EstadoContenedor, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EstadoContenedor estado) {
        if (estado == null) {
            return null;
        }
        return estado.getId();
    }

    @Override
    public EstadoContenedor convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }
        return EstadoContenedor.fromId(id);
    }
}