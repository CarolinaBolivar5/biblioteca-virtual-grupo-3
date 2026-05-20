package com.grupo3.bibliotecavirtual.model.dto;

import com.grupo3.bibliotecavirtual.model.entity.Autor;
import com.grupo3.bibliotecavirtual.model.entity.Categoria;
import com.grupo3.bibliotecavirtual.model.entity.Estado;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Schema(description = "POST/PUT /api/libros", example = """
        {"nombreLibro":"El principito","cantidadPaginas":96,"autor":{"id":1},"categoria":{"id":1},"estado":{"id":1}}
        """)
@Getter
@Setter
public class LibroDTO {
    private Long id;
    private String nombreLibro;
    private int cantidadPaginas;
    private String googleId;
    private String thumbnail;
    private String descripcion;
    private String autoresTexto;

    // Objetos completos en lugar de IDs
    private Autor autor;
    private Categoria categoria;
    private Estado estado;

    // Fechas de auditoría
    private LocalDate fechaRegistro;
    private LocalDate fechaModificacion;
}