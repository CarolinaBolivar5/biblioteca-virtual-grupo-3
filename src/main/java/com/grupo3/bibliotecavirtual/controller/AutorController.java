package com.grupo3.bibliotecavirtual.controller;

import com.grupo3.bibliotecavirtual.model.entity.Autor;
import com.grupo3.bibliotecavirtual.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Autores", description = "Gestión de autores")
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @Operation(summary = "Crear autores")
    @PostMapping
    public Autor guardar(@RequestBody Autor autor) {
        return autorService.guardarAutor(autor);
    }

    @Operation(summary = "Obtener todos los autores creados")
    @GetMapping
    public List<Autor> listar() {
        return autorService.listarAutores();
    }

    @Operation(summary = "Obtener un autor por ID")
    @GetMapping("/{id}")
    public Autor buscar(@PathVariable Long id) {
        return autorService.buscarPorId(id);
    }

    @Operation(summary = "Eliminar un autor por id")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        autorService.eliminarAutor(id);
    }
}