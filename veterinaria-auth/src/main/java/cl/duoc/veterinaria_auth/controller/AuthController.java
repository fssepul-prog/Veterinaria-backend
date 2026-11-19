package cl.duoc.veterinaria_auth.controller;

import cl.duoc.veterinaria_auth.dto.*;
import cl.duoc.veterinaria_auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticación", description = "Login, registro y renovación del JWT")
public class AuthController {
  private final AuthService auth;
  public AuthController(AuthService auth) { this.auth = auth; }

  @Operation(summary = "Inicia sesión y entrega un JWT")
  @PostMapping("/login")
  public AuthResponse login(@Valid @RequestBody LoginRequest request) {
    return auth.login(request);
  }

  @Operation(summary = "Crea una cuenta de dueño de mascota")
  @PostMapping("/registro")
  public ResponseEntity<AuthResponse> registro(@Valid @RequestBody RegistroRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(auth.registro(request));
  }

  @Operation(summary = "Entrega un token nuevo a partir de uno vigente")
  @PostMapping("/renovar")
  public AuthResponse renovar(Authentication autenticacion) {
    return auth.renovar(autenticacion.getName());
  }
}
