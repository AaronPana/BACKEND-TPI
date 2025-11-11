package com.backend_tpi.ms_camiones.services;

import com.backend_tpi.ms_camiones.dtos.TarifaDTO;
import com.backend_tpi.ms_camiones.exceptions.BaseException;
import com.backend_tpi.ms_camiones.models.Tarifa;
import com.backend_tpi.ms_camiones.repositories.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    public TarifaDTO actualizar(Integer id, TarifaDTO dto) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> BaseException.notFoundById("Tarifa", id));

        tarifa.setConcepto(dto.getConcepto());
        tarifa.setCostoXKilometro(dto.getCostoXKilometro());

        Tarifa saved = tarifaRepository.save(tarifa);
        return new TarifaDTO(saved.getIdTarifa(), saved.getConcepto(), saved.getCostoXKilometro());
    }

    // 🔹 NUEVO: Obtener tarifa por ID
    public TarifaDTO obtenerPorId(Integer id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> BaseException.notFoundById("Tarifa", id));

        return new TarifaDTO(
                tarifa.getIdTarifa(),
                tarifa.getConcepto(),
                tarifa.getCostoXKilometro()
        );
    }
}
