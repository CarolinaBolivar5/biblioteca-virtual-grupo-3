package com.grupo3.bibliotecavirtual.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "POST /categorias", example = """
        {"nombre":"Literatura","descripcion":"Obras clásicas y contemporáneas"}
        """)
@Getter
@Setter
public class CategoriaRequestDTO {    private String nombre;
    private String descripcion;
}
