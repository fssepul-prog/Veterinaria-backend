package cl.duoc.veterinaria_auth.service;

import cl.duoc.veterinaria_auth.dto.*;
import cl.duoc.veterinaria_auth.exception.*;
import cl.duoc.veterinaria_auth.model.Usuario;
import cl.duoc.veterinaria_auth.repository.UsuarioRepository;
import java.util.List;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
  private static final Set<String> ROLES = Set.of("ADMIN", "RECEPCIONISTA", "CLIENTE");

  private final UsuarioRepository usuarios;
  private final PasswordEncoder encoder;

  public UsuarioService(UsuarioRepository usuarios, PasswordEncoder encoder) {
    this.usuarios = usuarios;
    this.encoder = encoder;
  }

  public List<UsuarioResponse> listar() {
    return usuarios.findAll().stream().map(UsuarioResponse::desde).toList();
  }

  public UsuarioResponse crear(UsuarioRequest request) {
    validarRol(request.rol());
    if (usuarios.existsByCorreoIgnoreCase(request.correo())) {
      throw new ConflictoException("Ya existe una cuenta con ese correo");
    }
    Usuario usuario = new Usuario();
    usuario.setNombre(request.nombre().trim());
    usuario.setCorreo(request.correo().trim().toLowerCase());
    usuario.setTelefono(request.telefono());
    usuario.setRol(request.rol());
    usuario.setPassword(encoder.encode(request.password()));
    return UsuarioResponse.desde(usuarios.save(usuario));
  }

  public UsuarioResponse actualizar(Long id, UsuarioUpdateRequest cambios, String correoAdmin) {
    Usuario usuario = usuarios.findById(id)
      .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + id));
    boolean esPropio = usuario.getCorreo().equalsIgnoreCase(correoAdmin);
    if (esPropio && (cambios.rol() != null || cambios.activo() != null)) {
      throw new IllegalArgumentException("No puedes cambiar tu propio rol ni desactivar tu cuenta");
    }
    if (cambios.nombre() != null && !cambios.nombre().isBlank()) usuario.setNombre(cambios.nombre().trim());
    if (cambios.telefono() != null) usuario.setTelefono(cambios.telefono());
    if (cambios.rol() != null) {
      validarRol(cambios.rol());
      usuario.setRol(cambios.rol());
    }
    if (cambios.activo() != null) usuario.setActivo(cambios.activo());
    return UsuarioResponse.desde(usuarios.save(usuario));
  }

  private void validarRol(String rol) {
    if (!ROLES.contains(rol)) throw new IllegalArgumentException("Rol no válido: " + rol);
  }
}
