package com.grupo3.bibliotecavirtual.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "POST /autores", example = "{\"nombre\":\"Gabriel García Márquez\"}")
@Entity
@Table(name = "autores")
@Getter
@Setter
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Libro> libros = new ArrayList<>();

    public Autor() {
    }
}