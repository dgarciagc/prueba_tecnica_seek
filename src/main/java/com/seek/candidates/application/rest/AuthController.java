package com.seek.candidates.application.rest;

import com.seek.candidates.domain.dto.JwtDto;
import com.seek.candidates.domain.dto.LoginDto;
import com.seek.candidates.infraestructure.security.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
  }
  @Operation(summary = "Iniciar sesión", description = "Retorna un json con el token de respuesta")
  @ApiResponse(responseCode = "200", description = "guardado exitoso")
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDto loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getUsername(),
              loginRequest.getPassword()
          )
      );
      String token = jwtUtils.generateToken(authentication.getName());
      return ResponseEntity.ok(new JwtDto(token));
    } catch (BadCredentialsException e) {
      System.err.println("Error de credenciales: " + e.getMessage());
      return ResponseEntity.badRequest().body("Credenciales inválidas.");
    } catch (IllegalArgumentException e) {
      System.err.println("Argumento inválido en el login: " + e.getMessage());
      return ResponseEntity.badRequest().body("Datos de entrada inválidos.");
    } catch (Exception e) {
      System.err.println("Error inesperado: " + e.getMessage());
      return ResponseEntity.status(500).body("Error interno del servidor.");
    }
  }
}