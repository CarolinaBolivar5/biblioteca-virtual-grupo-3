package com.grupo3.bibliotecavirtual.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo3.bibliotecavirtual.model.entity.Estado;
import com.grupo3.bibliotecavirtual.service.EstadoService;

@RestController
@Tag(name = "Estado", description = "Gestión de estados")
@RequestMapping("/api/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @Operation(summary = "Obtener todos los estados creados")
    @GetMapping
    public List<Estado> listar() {
        return estadoService.listarEstados();
    }

    @Operation(summary = "Obtener un estado por ID")
    @GetMapping("/{id}")
    public Estado obtenerPorId(@PathVariable Long id) {
        return estadoService.obtenerEstadoPorId(id);
    }

    @Operation(summary = "Crear un estado")
    @PostMapping
    public Estado guardar(@RequestBody Estado estado) {
        return estadoService.guardarEstado(estado);
    }

    @Operation(summary = "Actualizar un estado por id")
    @PutMapping("/{id}")
    public Estado actualizar(@PathVariable Long id, @RequestBody Estado estado) {
        estado.setId(id);
        return estadoService.guardarEstado(estado);
    }

    @Operation(summary = "Eliminar un estado por id")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        estadoService.eliminarEstado(id);
    }
}