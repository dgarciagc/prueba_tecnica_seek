package com.seek.candidates.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
  @Schema(description = "Nombre de usuario", example = "admin")
  private String username;
  @Schema(description = "Contraseña del usuario", example = "123456")

  private String password;
}
