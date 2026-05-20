package com.grupo3.bibliotecavirtual.controller;

import com.grupo3.bibliotecavirtual.model.entity.Libro;
import com.grupo3.bibliotecavirtual.model.dto.LibroDTO;
import com.grupo3.bibliotecavirtual.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Libro", description = "Gestión de libros")
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Operation(summary = "Obtener todos los libros creados")
    @GetMapping
    public List<Libro> listar() {
        return libroService.listarLibros();
    }

    @Operation(summary = "Obtener un libro por ID")
    @GetMapping("/{id}")
    public Libro obtenerPorId(@PathVariable Long id) {
        return libroService.obtenerLibroPorId(id);
    }

    @Operation(summary = "Crear libro")
    @PostMapping
    public Libro guardar(@RequestBody LibroDTO libroDTO) {
        // Convertir DTO a entidad
        Libro libro = convertirDTOaEntidad(libroDTO);
        return libroService.guardarLibro(libro);
    }

    @Operation(summary = "Actualizar un libro por id")
    @PutMapping("/{id}")
    public Libro actualizar(@PathVariable Long id, @RequestBody LibroDTO libroDTO) {
        libroDTO.setId(id);
        Libro libro = convertirDTOaEntidad(libroDTO);
        return libroService.guardarLibro(libro);
    }

    @Operation(summary = "Eliminar un libro por id")
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        libroService.eliminarLibro(id);
    }

    private Libro convertirDTOaEntidad(LibroDTO dto) {
        Libro libro = new Libro();
        libro.setId(dto.getId());
        libro.setNombreLibro(dto.getNombreLibro());
        libro.setCantidadPaginas(dto.getCantidadPaginas());
        libro.setGoogleId(dto.getGoogleId());
        libro.setThumbnail(dto.getThumbnail());
        libro.setDescripcion(dto.getDescripcion());
        libro.setAutoresTexto(dto.getAutoresTexto());

        // Asignar objetos completos
        libro.setAutor(dto.getAutor());
        libro.setCategoria(dto.getCategoria());
        libro.setEstado(dto.getEstado());

        return libro;
    }
}