package com.backend_tpi.ms_rutas.external.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RutaDtoRes {

  private String idConsulta; // ID único de esta consulta
  private RutaAlternativaDtoRes rutaDirecta; // A -> B
  private List<RutaAlternativaDtoRes> rutasAlternativas; // rutas con depósitos
}
