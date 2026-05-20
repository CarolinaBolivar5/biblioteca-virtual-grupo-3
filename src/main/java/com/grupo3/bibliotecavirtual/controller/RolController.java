package com.grupo3.bibliotecavirtual.controller;

import com.grupo3.bibliotecavirtual.model.dto.RolDTO;
import com.grupo3.bibliotecavirtual.model.entity.Rol;
import com.grupo3.bibliotecavirtual.service.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Roles", description = "Gestión de roles")
@RequestMapping("/roles")
public class RolController {

    private final RolService service;

    public RolController(RolService service) {
        this.service = service;
    }

    @Operation(summary = "Listar Roles")
    @GetMapping
    public ResponseEntity<List<Rol>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Obtener un rol por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Rol> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Crear rol")
    @PostMapping
    public ResponseEntity<Rol> guardar(@RequestBody RolDTO dto) {
        Rol rol = new Rol();
        rol.setDescripcion(dto.getDescripcion());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(rol));
    }

    @Operation(summary = "Actualizar rol por ID")
    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizar(@PathVariable Long id, @RequestBody RolDTO dto) {
        Rol rol = new Rol();
        rol.setDescripcion(dto.getDescripcion());
        return ResponseEntity.ok(service.actualizar(id, rol));
    }

    @Operation(summary = "Eliminar un rol por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
