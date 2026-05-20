package com.grupo3.bibliotecavirtual.service;

import com.grupo3.bibliotecavirtual.model.entity.Rol;
import com.grupo3.bibliotecavirtual.model.entity.Usuario;
import com.grupo3.bibliotecavirtual.repository.UsuarioRepository;
import com.grupo3.bibliotecavirtual.security.RolNames;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserSecurityService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " not found."));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .roles(resolverNombreRol(usuario.getRol()))
                .build();
    }

    private String resolverNombreRol(Rol rol) {
        if (rol == null || rol.getDescripcion() == null) {
            return RolNames.USUARIO;
        }
        return rol.getDescripcion().name();
    }
}
