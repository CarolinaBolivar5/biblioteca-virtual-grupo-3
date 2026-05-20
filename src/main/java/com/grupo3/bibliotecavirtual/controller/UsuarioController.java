package com.grupo3.bibliotecavirtual.controller;

import com.grupo3.bibliotecavirtual.model.dto.UsuarioDTO;
import com.grupo3.bibliotecavirtual.model.entity.Perfil;
import com.grupo3.bibliotecavirtual.model.entity.Rol;
import com.grupo3.bibliotecavirtual.model.entity.Usuario;
import com.grupo3.bibliotecavirtual.service.PerfilService;
import com.grupo3.bibliotecavirtual.service.RolService;
import com.grupo3.bibliotecavirtual.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Usuarios", description = "Gestión de usuarios")
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final RolService rolService;
    private final PerfilService perfilService;

    public UsuarioController(UsuarioService service, RolService rolService, PerfilService perfilService) {
        this.service = service;
        this.rolService = rolService;
        this.perfilService = perfilService;
    }

    @Operation(summary = "Listar Usuarios")
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener un usuario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Obtener un usuario por Email")
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.buscarPorEmail(email));
    }

    @Operation(summary = "Crear usuario")
    @PostMapping
    public ResponseEntity<Usuario> guardar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());

        if (dto.getRolId() != null) {
            Rol rol = rolService.buscarPorId(dto.getRolId());
            usuario.setRol(rol);
        }
        if (dto.getPerfilId() != null) {
            Perfil perfil = perfilService.buscarPorId(dto.getPerfilId());
            usuario.setPerfil(perfil);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }

    @Operation(summary = "Actualizar usuario por ID")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());

        if (dto.getRolId() != null) {
            Rol rol = rolService.buscarPorId(dto.getRolId());
            usuario.setRol(rol);
        }

        return ResponseEntity.ok(service.actualizar(id, usuario));
    }

    @Operation(summary = "Eliminar un usuario por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
