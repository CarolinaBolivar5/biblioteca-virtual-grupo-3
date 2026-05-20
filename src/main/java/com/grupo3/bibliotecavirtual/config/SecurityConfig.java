package com.grupo3.bibliotecavirtual.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/scalar/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/usuarios",
                                "/perfiles/"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET, "/usuarios").permitAll()

                        .requestMatchers(HttpMethod.GET,
                                "/api/libros",
                                "/api/libros/**",
                                "/categorias",
                                "/categorias/**",
                                "/autores",
                                "/autores/**",
                                "/api/estados",
                                "/api/estados/**",
                                "/roles",
                                "/roles/**",
                                "/perfiles/**",
                                "/prestamos/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/libros", "/api/libros/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/libros", "/api/libros/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/categorias", "/categorias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/autores", "/autores/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/estados", "/api/estados/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/estados", "/api/estados/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuarios", "/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/usuarios", "/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/roles", "/roles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/roles", "/roles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/perfiles/**").hasRole("USUARIO")
                        .requestMatchers(HttpMethod.POST,"/perfiles/**").hasRole("USUARIO")
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
