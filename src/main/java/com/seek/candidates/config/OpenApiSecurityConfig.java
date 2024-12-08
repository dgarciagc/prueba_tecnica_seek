package com.seek.candidates.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecuritySchemes({
    @SecurityScheme(
        name = "BearerAuth",                  // Nombre del esquema, usado en @SecurityRequirement
        type = SecuritySchemeType.HTTP,      // Tipo de esquema: HTTP
        scheme = "bearer",                   // Esquema: Bearer
        bearerFormat = "JWT"                 // Formato del token: JWT
    )
})
public class OpenApiSecurityConfig {
}