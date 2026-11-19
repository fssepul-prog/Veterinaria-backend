package cl.duoc.veterinaria_auth.dto;

public record AuthResponse(String token, String correo, String nombre, String rol) {}
