package cl.duoc.veterinaria_auth.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(
  @NotBlank @Email String correo,
  @NotBlank String password
) {}
