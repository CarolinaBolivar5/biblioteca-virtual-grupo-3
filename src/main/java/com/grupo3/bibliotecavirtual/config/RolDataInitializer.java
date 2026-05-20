package com.grupo3.bibliotecavirtual.config;

import com.grupo3.bibliotecavirtual.model.entity.Rol;
import com.grupo3.bibliotecavirtual.model.enums.TipoRol;
import com.grupo3.bibliotecavirtual.repository.RolRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RolDataInitializer implements ApplicationRunner {

    private final RolRepository rolRepository;

    public RolDataInitializer(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        crearRolSiNoExiste(TipoRol.ADMIN);
        crearRolSiNoExiste(TipoRol.USUARIO);
    }

    private void crearRolSiNoExiste(TipoRol descripcion) {
        if (rolRepository.findByDescripcion(descripcion).isEmpty()) {
            Rol rol = new Rol();
            rol.setDescripcion(descripcion);
            rolRepository.save(rol);
        }
    }
}
