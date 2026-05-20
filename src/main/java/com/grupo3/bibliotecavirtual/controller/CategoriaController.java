package com.grupo3.bibliotecavirtual.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo3.bibliotecavirtual.model.dto.CategoriaRequestDTO;
import com.grupo3.bibliotecavirtual.model.dto.CategoriaResponseDTO;
import com.grupo3.bibliotecavirtual.service.impl.CategoriaServiceImpl;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaServiceImpl service;

    public CategoriaController(CategoriaServiceImpl service) {
        this.service = service;
    }
    @Operation(summary = "Obtener todas las categorias creadas")
    @GetMapping
    public List<CategoriaResponseDTO> listar() {
        return service.listarDTO();
    }
    @Operation(summary = "Crear categorias")
    @PostMapping
    public CategoriaResponseDTO guardar(@RequestBody CategoriaRequestDTO dto) {
        return service.guardarDTO(dto);
    }
}