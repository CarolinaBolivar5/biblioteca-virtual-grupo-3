package com.grupo3.bibliotecavirtual.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Schema(description = "POST /prestamos (libro desde Google Books)", example = """
        {"libro":{"nombreLibro":"Cien años de soledad","googleId":"abc123","autoresTexto":"G. García Márquez"},"perfilId":1,"fechaDevolucion":"2026-05-20"}
        """)
@Getter
@Setter
public class PrestamoRequest {
    // En lugar de campos individuales, usar objeto Libro completo
    private LibroDTO libro;

    private Long perfilId;

    // Fecha de devolución seleccionada por el usuario
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaDevolucion;

    // También podemos incluir el perfil completo si es necesario
    // private Perfil perfil;
}