package cl.duoc.veterinaria_auth.dto;

// Todos los campos son opcionales: solo se cambia lo que llega distinto de null
public record UsuarioUpdateRequest(String nombre, String telefono, String rol, Boolean activo) {}
