package com.grupo3.bibliotecavirtual.controller;

import com.grupo3.bibliotecavirtual.model.entity.Perfil;
import com.grupo3.bibliotecavirtual.model.entity.Prestamo;
import com.grupo3.bibliotecavirtual.service.PerfilService;
import com.grupo3.bibliotecavirtual.service.PrestamoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Perfiles", description = "Gestión de perfiles de usuarios")
@RequestMapping("/perfiles")
public class PerfilController {

    private final PerfilService service;
    private final PrestamoService prestamoService;

    public PerfilController(PerfilService service, PrestamoService prestamoService) {
        this.service = service;
        this.prestamoService = prestamoService;
    }

    @Operation(summary = "Listar perfiles")
    @GetMapping
    public ResponseEntity<List<Perfil>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Buscar perfil por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Perfil> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Obtener libros prestados por perfil")
    @GetMapping("/{id}/libros-prestados")
    public ResponseEntity<List<Prestamo>> obtenerLibrosPrestados(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.obtenerPorPerfil(id));
    }

    @Operation(summary = "Crear perfil")
    @PostMapping
    public ResponseEntity<Perfil> guardar(@RequestBody Perfil perfil) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(perfil));
    }

    @Operation(summary = "Actualizar perfil")
    @PutMapping("/{id}")
    public ResponseEntity<Perfil> actualizar(@PathVariable Long id, @RequestBody Perfil perfil) {
        return ResponseEntity.ok(service.actualizar(id, perfil));
    }

    @Operation(summary = "Eliminar perfil")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
