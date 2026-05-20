package com.grupo3.bibliotecavirtual.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "POST/PUT /roles", example = "{\"descripcion\":\"USUARIO\"}")
@Getter
@Setter
public class RolDTO {
    private Long id;
    private String descripcion;
}
