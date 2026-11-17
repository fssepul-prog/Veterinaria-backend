package cl.duoc.veterinaria_auth.service;

import cl.duoc.veterinaria_auth.dto.*;
import cl.duoc.veterinaria_auth.exception.ConflictoException;
import cl.duoc.veterinaria_auth.model.Usuario;
import cl.duoc.veterinaria_auth.repository.UsuarioRepository;
import cl.duoc.veterinaria_auth.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UsuarioRepository usuarios;
  private final PasswordEncoder encoder;
  private final JwtService jwt;

  public AuthService(UsuarioRepository usuarios, PasswordEncoder encoder, JwtService jwt) {
    this.usuarios = usuarios;
    this.encoder = encoder;
    this.jwt = jwt;
  }

  public AuthResponse login(LoginRequest request) {
    Usuario usuario = usuarios.findByCorreoIgnoreCase(request.correo())
      .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));
    if (!encoder.matches(request.password(), usuario.getPassword())) {
      throw new BadCredentialsException("Credenciales inválidas");
    }
    return respuesta(usuario);
  }

  // Toda cuenta creada desde el registro público es de dueño de mascota
  public AuthResponse registro(RegistroRequest request) {
    if (usuarios.existsByCorreoIgnoreCase(request.correo())) {
      throw new ConflictoException("Ya existe una cuenta con ese correo");
    }
    Usuario usuario = new Usuario();
    usuario.setNombre(request.nombre().trim());
    usuario.setCorreo(request.correo().trim().toLowerCase());
    usuario.setTelefono(request.telefono());
    usuario.setPassword(encoder.encode(request.password()));
    usuario.setRol("CLIENTE");
    return respuesta(usuarios.save(usuario));
  }

  // El correo viene del token vigente, que el filtro ya validó
  public AuthResponse renovar(String correo) {
    Usuario usuario = usuarios.findByCorreoIgnoreCase(correo)
      .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));
    return respuesta(usuario);
  }

  private AuthResponse respuesta(Usuario usuario) {
    if (!usuario.isActivo()) {
      throw new DisabledException("Tu cuenta está desactivada. Contacta a la clínica.");
    }
    return new AuthResponse(jwt.generar(usuario), usuario.getCorreo(),
      usuario.getNombre(), usuario.getRol());
  }
}
