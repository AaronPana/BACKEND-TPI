package com.backend_tpi.ms_camiones.services;

import com.backend_tpi.ms_camiones.dtos.TarifaDTO;
import com.backend_tpi.ms_camiones.exceptions.BaseException;
import com.backend_tpi.ms_camiones.models.Tarifa;
import com.backend_tpi.ms_camiones.repositories.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    public List<TarifaDTO> listarTodas() {
        return tarifaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TarifaDTO obtenerPorId(Integer id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> BaseException.notFoundById("Tarifa", id));
        return toDTO(tarifa);
    }

    public TarifaDTO crear(TarifaDTO dto) {
        Tarifa tarifa = new Tarifa();
        tarifa.setConcepto(dto.getConcepto());
        tarifa.setCostoXKilometro(dto.getCostoXKilometro());
        Tarifa guardada = tarifaRepository.save(tarifa);
        return toDTO(guardada);
    }

    public TarifaDTO actualizar(Integer id, TarifaDTO dto) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> BaseException.notFoundById("Tarifa", id));

        tarifa.setConcepto(dto.getConcepto());
        tarifa.setCostoXKilometro(dto.getCostoXKilometro());

        Tarifa guardada = tarifaRepository.save(tarifa);
        return toDTO(guardada);
    }

    public void eliminar(Integer id) {
        if (!tarifaRepository.existsById(id)) {
            throw BaseException.notFoundById("Tarifa", id);
        }
        tarifaRepository.deleteById(id);
    }

    private TarifaDTO toDTO(Tarifa tarifa) {
        return new TarifaDTO(
                tarifa.getIdTarifa(),
                tarifa.getConcepto(),
                tarifa.getCostoXKilometro()
        );
    }
}
