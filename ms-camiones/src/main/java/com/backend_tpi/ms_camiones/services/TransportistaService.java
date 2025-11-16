package com.backend_tpi.ms_camiones.services;

import com.backend_tpi.ms_camiones.dtos.responses.TransportistaResponse;
import com.backend_tpi.ms_camiones.exceptions.BaseException;
import com.backend_tpi.ms_camiones.models.Transportista;
import com.backend_tpi.ms_camiones.repositories.TransportistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend_tpi.ms_camiones.exceptions.BaseException;

import java.util.List;

@Service
public class TransportistaService {

    @Autowired
    private TransportistaRepository transportistaRepository;

    public List<Transportista> listarTodos() {
        return transportistaRepository.findAll();
    }

    public Transportista obtenerPorLegajo(Integer legajo) {
        return transportistaRepository.findById(legajo)
                .orElseThrow(() -> BaseException.notFoundById("Transportista", legajo));
    }

    public Integer obtenerLegajoAleatorio() {
        var transportistas = transportistaRepository.findAll();

        if (transportistas.isEmpty()) {
            throw BaseException.badRequest("No hay transportistas cargados en la base");
        }

        int indexAleatorio = (int) (Math.random() * transportistas.size());
        return transportistas.get(indexAleatorio).getLegajo();
    }

    public Transportista crear(Transportista transportista) {
        return transportistaRepository.save(transportista);
    }

    public Transportista actualizar(Integer legajo, Transportista datos) {
        Transportista existente = obtenerPorLegajo(legajo);
        existente.setNombre(datos.getNombre());
        existente.setApellido(datos.getApellido());
        existente.setTelefono(datos.getTelefono());
        existente.setEmail(datos.getEmail());
        existente.setDireccion(datos.getDireccion());
        existente.setCiudad(datos.getCiudad());
        return transportistaRepository.save(existente);
    }

    public void eliminar(Integer legajo) {
        Transportista existente = obtenerPorLegajo(legajo);
        transportistaRepository.delete(existente);
    }


}


