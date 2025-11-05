package com.backend_tpi.ms_traslados.mappers;

import com.backend_tpi.ms_traslados.dtos.responses.ClienteDtoRes;
import com.backend_tpi.ms_traslados.models.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

  public ClienteDtoRes clienteToDtoRes(Cliente cliente) {
    ClienteDtoRes clienteDtoRes = new ClienteDtoRes();
    clienteDtoRes.setNroDocumento(cliente.getNroDocumento());
    clienteDtoRes.setNombre(cliente.getNombre());
    clienteDtoRes.setApellido(clienteDtoRes.getApellido());
    clienteDtoRes.setTelefono(clienteDtoRes.getTelefono());
    clienteDtoRes.setEmail(cliente.getEmail());
    clienteDtoRes.setDireccion(cliente.getDireccion());
    clienteDtoRes.setIdCiudad(cliente.getIdCiudad()); // Probar llamar a otro servicio para obtener la ciudad
    String tieneTraslados = cliente.getTraslados().isEmpty() ? "NO" : "SI";
    clienteDtoRes.setTieneTraslados(tieneTraslados);
    return clienteDtoRes;
  }
}
