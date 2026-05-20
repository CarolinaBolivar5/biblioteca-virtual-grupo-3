package com.grupo3.bibliotecavirtual.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "POST/PUT /usuarios", example = """
        {"email":"maria@ejemplo.com","password":"MiClave123","rolId":2,"perfilId":1}
        """)
@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String email;
    private String password;
    private Long rolId;
    private Long perfilId;
}
