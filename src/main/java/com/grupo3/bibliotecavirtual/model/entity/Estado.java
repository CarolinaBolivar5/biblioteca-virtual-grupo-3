package com.grupo3.bibliotecavirtual.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "POST/PUT /api/estados", example = "{\"detalle\":\"Disponible\"}")
@Entity
@Table(name = "estado")
@Getter
@Setter
public class Estado extends BaseEntity {

    private String detalle;

    // Un estado tiene muchos libros
    @OneToMany(mappedBy = "estado")
    private List<Libro> libros;
}