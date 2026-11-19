package cl.duoc.veterinaria_auth.dto;

import jakarta.validation.constraints.*;

public record UsuarioRequest(
  @NotBlank String nombre,
  @NotBlank @Email String correo,
  String telefono,
  @NotBlank String rol,
  @NotBlank @Size(min = 8, message = "debe tener al menos 8 caracteres") String password
) {}
