package com.grupo3.bibliotecavirtual.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "POST/PUT /perfiles", example = """
        {"tipoDocumento":"CC","numeroDocumento":"1234567890","nombre":"María","apellido":"García","direccion":"Calle 10","telefono":"3001234567"}
        """)
@Entity
@Table(name = "perfil")
@Getter
@Setter
public class Perfil extends BaseEntity {

  private String tipoDocumento;
  private String numeroDocumento;
  private String nombre;
  private String apellido;
  private String direccion;
  private String telefono;

    @OneToOne(mappedBy = "perfil")
    @JsonBackReference
    private Usuario usuario;

    @OneToMany(mappedBy = "perfil")
    private List<Prestamo> prestamos;

}
