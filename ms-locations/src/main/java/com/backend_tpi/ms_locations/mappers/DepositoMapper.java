package com.backend_tpi.ms_locations.mappers;

import com.backend_tpi.ms_locations.dtos.responses.DepositoDtoRes;
import com.backend_tpi.ms_locations.external.dtos.responses.DepositoAlternativoDtoRes;
import org.springframework.stereotype.Component;

@Component
public class DepositoMapper {

  public DepositoDtoRes alternativoToDepositoDtoRes(DepositoAlternativoDtoRes depositoAlternativoDtoRes) {
    System.out.println(depositoAlternativoDtoRes);
    DepositoDtoRes depositoDtoRes = new DepositoDtoRes();
    depositoDtoRes.setIdDeposito(depositoAlternativoDtoRes.getIdDeposito());
    depositoDtoRes.setNombre(depositoAlternativoDtoRes.getNombre());
    depositoDtoRes.setLatitud(depositoAlternativoDtoRes.getLatitud());
    depositoDtoRes.setLongitud(depositoAlternativoDtoRes.getLongitud());
    depositoDtoRes.setDireccion(depositoAlternativoDtoRes.getDireccion());
    depositoDtoRes.setDistanciaDesdePuntoMedioRuta(0.0);
    return depositoDtoRes;
  }
}
