package com.backend_tpi.ms_traslados.mappers;

import com.backend_tpi.ms_traslados.dtos.responses.ClienteDtoRes;
import com.backend_tpi.ms_traslados.dtos.responses.FullClienteDtoRes;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoSinClienteDtoRes;
import com.backend_tpi.ms_traslados.models.Cliente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteMapper {

  public ClienteDtoRes clienteToDtoRes(Cliente cliente, String ciudadProvincia) {
    ClienteDtoRes clienteDtoRes = new ClienteDtoRes();
    clienteDtoRes.setNroDocumento(cliente.getNroDocumento());
    clienteDtoRes.setNombreCompleto(cliente.getNombre() + " " + cliente.getApellido());
    clienteDtoRes.setTelefono(cliente.getTelefono());
    clienteDtoRes.setCiudadProvincia(ciudadProvincia);
    String tieneTraslados = cliente.getTraslados().isEmpty() ? "NO" : "SI";
    clienteDtoRes.setTieneTraslados(tieneTraslados);
    return clienteDtoRes;
  }

  public FullClienteDtoRes fullClienteToDtoRes(Cliente cliente, String ciudadProvincia, List<TrasladoSinClienteDtoRes> traslados) {
    FullClienteDtoRes fullClienteDtoRes = new FullClienteDtoRes();
    fullClienteDtoRes.setNroDocumento(cliente.getNroDocumento());
    fullClienteDtoRes.setNombreCompleto(cliente.getNombre() + " " + cliente.getApellido());
    fullClienteDtoRes.setTelefono(cliente.getTelefono());
    fullClienteDtoRes.setEmail(cliente.getEmail());
    fullClienteDtoRes.setDireccion(cliente.getDireccion());
    fullClienteDtoRes.setCiudadProvincia(ciudadProvincia);
    fullClienteDtoRes.setTraslados(traslados);
    return fullClienteDtoRes;
  }
}
