package com.grupo3.bibliotecavirtual.controller;

import com.grupo3.bibliotecavirtual.model.entity.Prestamo;
import com.grupo3.bibliotecavirtual.service.PrestamoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.grupo3.bibliotecavirtual.model.dto.PrestamoRequest;

import java.util.List;

@RestController
@Tag(name = "Préstamos", description = "Gestión de préstamos de libros")
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService service;

    public PrestamoController(PrestamoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar préstamos")
    @GetMapping
    public ResponseEntity<List<Prestamo>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @Operation(summary = "Listar préstamos por perfil")
    @GetMapping("/perfil/{perfilId}")
    public ResponseEntity<List<Prestamo>> obtenerPorPerfil(@PathVariable Long perfilId) {
        System.out.println("Obteniendo préstamos para perfilId: " + perfilId);
        List<Prestamo> prestamos = service.obtenerPorPerfil(perfilId);
        System.out.println("Préstamos encontrados: " + prestamos.size());
        return ResponseEntity.ok(prestamos);
    }

    @Operation(summary = "Buscar préstamo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Crear préstamo")
    @PostMapping
    public ResponseEntity<Prestamo> guardar(@RequestBody PrestamoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crearPrestamoDesdeGoogle(request));
    }

    @Operation(summary = "Crear préstamo simple")
    @PostMapping("/simple")
    public ResponseEntity<Prestamo> crearPrestamoSimple(@RequestBody Prestamo prestamo) {
        System.out.println("Creando préstamo simple con libro_id: " + 
            (prestamo.getLibro() != null ? prestamo.getLibro().getId() : "NULL") +
            " y perfil_id: " + 
            (prestamo.getPerfil() != null ? prestamo.getPerfil().getId() : "NULL"));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardar(prestamo));
    }

    @Operation(summary = "Actualizar préstamo")
    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> actualizar(@PathVariable Long id, @RequestBody Prestamo prestamo) {
        return ResponseEntity.ok(service.actualizar(id, prestamo));
    }

    @Operation(summary = "Asignar perfil a préstamo")
    @PutMapping("/{id}/perfil/{perfilId}")
    public ResponseEntity<Prestamo> asignarPerfilAPrestamo(@PathVariable Long id, @PathVariable Long perfilId) {
        System.out.println("Asignando perfil " + perfilId + " al préstamo " + id);
        return ResponseEntity.ok(service.asignarPerfil(id, perfilId));
    }

    @Operation(summary = "Eliminar préstamo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
