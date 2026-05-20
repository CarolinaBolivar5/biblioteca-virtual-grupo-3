package com.grupo3.bibliotecavirtual.service.impl;

import com.grupo3.bibliotecavirtual.model.entity.Rol;
import com.grupo3.bibliotecavirtual.model.entity.Usuario;
import com.grupo3.bibliotecavirtual.model.enums.TipoRol;
import com.grupo3.bibliotecavirtual.repository.RolRepository;
import com.grupo3.bibliotecavirtual.repository.UsuarioRepository;
import com.grupo3.bibliotecavirtual.service.UsuarioService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(
            UsuarioRepository repository,
            RolRepository rolRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Usuario> listar() {
        return repository.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        if (repository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + usuario.getEmail());
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        if (usuario.getRol() == null) {
            usuario.setRol(obtenerRolPorDefecto());
        }
        return repository.save(usuario);
    }

    @Override
    public Usuario actualizar(Long id, Usuario usuario) {
        Usuario existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        existente.setEmail(usuario.getEmail());
        actualizarPassword(existente, usuario.getPassword());

        if (usuario.getPerfil() != null) {
            existente.setPerfil(usuario.getPerfil());
        }
        if (usuario.getRol() != null) {
            existente.setRol(usuario.getRol());
        }

        return repository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    private Rol obtenerRolPorDefecto() {
        return rolRepository.findByDescripcion(TipoRol.USUARIO)
                .orElseThrow(() -> new RuntimeException(
                        "Rol " + TipoRol.USUARIO + " no encontrado. Reinicie la aplicación para inicializar roles."
                ));
    }

    private void actualizarPassword(Usuario existente, String password) {
        if (password == null || password.isBlank()) {
            return;
        }
        if (password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$")) {
            existente.setPassword(password);
            return;
        }
        if (!passwordEncoder.matches(password, existente.getPassword())) {
            existente.setPassword(passwordEncoder.encode(password));
        }
    }
}
