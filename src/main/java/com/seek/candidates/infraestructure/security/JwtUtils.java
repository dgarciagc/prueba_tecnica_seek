package com.seek.candidates.infraestructure.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
  @Value("${secretKeyJWT}")
  private String secretKey;

  // Generar un token JWT
  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24)) // Token válido por 1 día // el ultimo numero son las horas: si quieres una hora poner 1
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

  // Validar un token JWT
  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      System.out.println("Invalid JWT token: " + e.getMessage());
      return false;
    }
  }

  // Extraer el nombre de usuario desde el token JWT
  public String getUsernameFromToken(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}
