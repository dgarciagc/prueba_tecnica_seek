package com.seek.candidates.application.rest;

import com.seek.candidates.domain.dto.CandidateDto;
import com.seek.candidates.domain.dto.JwtDto;
import com.seek.candidates.domain.dto.LoginDto;
import com.seek.candidates.infraestructure.security.JwtUtils;
import com.seek.candidates.utils.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
@Log4j2
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
  public ResponseEntity<ResultResponse<?>> login(@RequestBody LoginDto loginRequest) {
    ResultResponse<JwtDto> response = new ResultResponse<>();
    try {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginRequest.getUsername(),
              loginRequest.getPassword()
          )
      );
      response.setSuccess(true);
      response.setMsg("Token generado");
      String token = jwtUtils.generateToken(authentication.getName());
      JwtDto jwtDto = new JwtDto();
      jwtDto.setToken(token);
      response.setData(jwtDto);
      return ResponseEntity.ok(response);
    } catch (BadCredentialsException e) {
      response.setMsg(e.getMessage());
      System.err.println("Error de credenciales: " + e.getMessage());
      response.setMsg("Credenciales inválidas.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (IllegalArgumentException e) {
      System.err.println("Argumento inválido en el login: " + e.getMessage());
      response.setMsg(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } catch (Exception e) {
      System.err.println("Error inesperado: " + e.getMessage());
      response.setMsg(e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }
}