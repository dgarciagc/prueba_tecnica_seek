package com.seek.candidates.infraestructure.security;

import com.seek.candidates.domain.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration

public class SecurityConfig {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomUserDetailsService userDetailsService; // Inyección del servicio personalizado


  public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomUserDetailsService userDetailsService) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.userDetailsService = userDetailsService;

  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(request -> {
          var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
          corsConfiguration.setAllowedOrigins(List.of(
              "https://pruebatecnicaseek-production.up.railway.app", // Dominio de Railway
              "http://localhost:8085" // Permitir localhost para pruebas locales
          ));
          corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
          corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
          corsConfiguration.setAllowCredentials(true); // Permitir cookies o autenticación
          return corsConfiguration;
        }))
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/auth/**").permitAll() // Permitir acceso sin autenticación
                .requestMatchers(
                    "/v3/api-docs/**",  // Permitir acceso a los JSON de OpenAPI
                    "/swagger-ui/**",   // Permitir acceso a Swagger UI
                    "/swagger-ui.html"  // Permitir acceso al archivo Swagger UI
                ).permitAll()
                .anyRequest().authenticated() // Requerir autenticación para las demás solicitudes
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Añadir el filtro JWT

    return http.build();
  }
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
