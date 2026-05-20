package com.grupo3.bibliotecavirtual.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        final String basicAuth = "basicAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("API Biblioteca Virtual")
                        .description("""
                                Backend para un sistema simple para gestion de una biblioteca virtual
                                
                                Permite administrar **Usuarios**, **Libros** y **Solicitudes** con:
                                - Herencia JPA (`@MappedSuperclass`)
                                - Enumeraciones (`@Enumerated`)
                                - Auditoría automática (`@CreatedDate` / `@LastModifiedDate`)
                                """)
                        .version("1.0.0")
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
                .servers(List.of(new Server().url("http://localhost:8080").description("Local")))
                .servers(List.of(new Server().url("https://biblioteca-virtual-grupo-3.onrender.com").description("Producción")))
                .components(new Components().addSecuritySchemes(basicAuth,
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
                .addSecurityItem(new SecurityRequirement().addList(basicAuth));
    }
}
