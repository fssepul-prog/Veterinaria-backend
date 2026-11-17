package cl.duoc.veterinaria_auth.dto;

import cl.duoc.veterinaria_auth.model.Usuario;

// Nunca incluye la contraseña
public record UsuarioResponse(Long id, String nombre, String correo, String telefono,
    String rol, boolean activo) {
  public static UsuarioResponse desde(Usuario u) {
    return new UsuarioResponse(u.getId(), u.getNombre(), u.getCorreo(),
      u.getTelefono(), u.getRol(), u.isActivo());
  }
}
