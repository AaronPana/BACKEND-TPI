package com.backend_tpi.ms_traslados.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "CLIENTES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

  @Id
  @Column(name = "NRO_DOCUMENTO", nullable = false, length = 10)
  private long nroDocumento;

  @Column(name = "NOMBRE", nullable = false, length = 80)
  private String nombre;

  @Column(name = "APELLIDO", length = 80)
  private String apellido;

  @Column(name = "TELEFONO", length = 20)
  private String telefono;

  @Column(name = "EMAIL", length = 100)
  private String email;

  @Column(name = "DIRECCION", length = 200)
  private String direccion;

  @Column(name = "ID_CIUDAD", nullable = false)
  private long idCiudad;

  @OneToMany(mappedBy = "cliente")
  private List<Traslado> traslados;
}
