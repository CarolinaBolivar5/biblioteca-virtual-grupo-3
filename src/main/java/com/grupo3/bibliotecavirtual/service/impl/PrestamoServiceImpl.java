package com.grupo3.bibliotecavirtual.service.impl;

import com.grupo3.bibliotecavirtual.model.entity.Prestamo;
import com.grupo3.bibliotecavirtual.repository.PrestamoRepository;
import com.grupo3.bibliotecavirtual.service.PrestamoService;
import org.springframework.stereotype.Service;
import com.grupo3.bibliotecavirtual.model.dto.PrestamoRequest;
import com.grupo3.bibliotecavirtual.model.entity.Libro;
import com.grupo3.bibliotecavirtual.model.entity.Perfil;
import com.grupo3.bibliotecavirtual.repository.LibroRepository;
import com.grupo3.bibliotecavirtual.repository.PerfilRepository;
import com.grupo3.bibliotecavirtual.repository.AutorRepository;
import com.grupo3.bibliotecavirtual.repository.CategoriaRepository;
import com.grupo3.bibliotecavirtual.repository.EstadoRepository;
import com.grupo3.bibliotecavirtual.model.enums.EstadoPrestamo;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository repository;

    public PrestamoServiceImpl(PrestamoRepository repository) {
        this.repository = repository;
        
    }

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
private PerfilRepository perfilRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Override
    public List<Prestamo> listar() {
        return repository.findAll();
    }

    @Override
    public Prestamo guardar(Prestamo prestamo) {
        return repository.save(prestamo);
    }

    @Override
    public Prestamo actualizar(Long id, Prestamo prestamo) {
        Prestamo existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado con id: " + id));

        if (prestamo.getLibro() != null) {
            existente.setLibro(prestamo.getLibro());
        }
        if (prestamo.getPerfil() != null) {
            existente.setPerfil(prestamo.getPerfil());
        }
        existente.setFechaPrestamo(prestamo.getFechaPrestamo());
        existente.setEstado(prestamo.getEstado());

        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Prestamo no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Prestamo buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado con id: " + id));
    }

@Override
public Prestamo crearPrestamoDesdeGoogle(PrestamoRequest request) {
    if (request.getLibro() == null) {
        throw new RuntimeException("El libro es requerido para crear un préstamo");
    }

    // Usar el libro del DTO en lugar de campos individuales
    Libro libro = convertirDTOaEntidad(request.getLibro());

    // Buscar si ya existe un libro con el mismo googleId
    if (libro.getGoogleId() != null) {
        Libro libroExistente = libroRepository.findByGoogleId(libro.getGoogleId());
        if (libroExistente != null) {
            libro = libroExistente;
        } else {
            libro = libroRepository.save(libro);
        }
    } else {
        libro = libroRepository.save(libro);
    }

    Perfil perfil = perfilRepository.findById(request.getPerfilId())
            .orElseThrow(() -> new RuntimeException("Perfil no encontrado con ID: " + request.getPerfilId()));

    Prestamo prestamo = new Prestamo();
    prestamo.setLibro(libro);
    prestamo.setPerfil(perfil);
    prestamo.setFechaPrestamo(LocalDate.now());
    prestamo.setFechaDevolucion(request.getFechaDevolucion());
    prestamo.setEstado(EstadoPrestamo.Prestado);

    return repository.save(prestamo);
}

private Libro convertirDTOaEntidad(com.grupo3.bibliotecavirtual.model.dto.LibroDTO dto) {
    if (dto == null) return null;

    Libro libro = new Libro();
    libro.setId(dto.getId());
    libro.setNombreLibro(dto.getNombreLibro());
    libro.setCantidadPaginas(dto.getCantidadPaginas());
    libro.setGoogleId(dto.getGoogleId());
    libro.setThumbnail(dto.getThumbnail());
    libro.setDescripcion(dto.getDescripcion());
    libro.setAutoresTexto(dto.getAutoresTexto());

    // Resolver relaciones desde BD para evitar entidades transientes
    if (dto.getAutor() != null && dto.getAutor().getId() != null) {
        libro.setAutor(autorRepository.findById(dto.getAutor().getId()).orElse(null));
    }
    if (dto.getCategoria() != null && dto.getCategoria().getId() != null) {
        libro.setCategoria(categoriaRepository.findById(dto.getCategoria().getId()).orElse(null));
    }
    if (dto.getEstado() != null && dto.getEstado().getId() != null) {
        libro.setEstado(estadoRepository.findById(dto.getEstado().getId()).orElse(null));
    }

    return libro;
}

@Override
public List<Prestamo> obtenerPorPerfil(Long perfilId) {
    return repository.findByPerfilId(perfilId);
}

@Override
public Prestamo asignarPerfil(Long prestamoId, Long perfilId) {
    Prestamo prestamo = repository.findById(prestamoId)
            .orElseThrow(() -> new RuntimeException("Prestamo no encontrado con id: " + prestamoId));
    
    Perfil perfil = perfilRepository.findById(perfilId)
            .orElseThrow(() -> new RuntimeException("Perfil no encontrado con id: " + perfilId));
    
    prestamo.setPerfil(perfil);
    return repository.save(prestamo);
}

}