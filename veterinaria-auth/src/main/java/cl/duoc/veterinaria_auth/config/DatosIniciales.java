package cl.duoc.veterinaria_auth.config;

import cl.duoc.veterinaria_auth.model.Usuario;
import cl.duoc.veterinaria_auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

// Crea el primer administrador; los demás usuarios se crean desde la aplicación
@Configuration
public class DatosIniciales {
  @Bean CommandLineRunner administradorInicial(UsuarioRepository repo,
      PasswordEncoder encoder, @Value("${app.admin.password}") String password) {
    return args -> {
      if (repo.findByCorreoIgnoreCase("admin@sanmarcos.cl").isEmpty()) {
        Usuario u = new Usuario();
        u.setNombre("Administrador");
        u.setCorreo("admin@sanmarcos.cl");
        u.setPassword(encoder.encode(password));
        u.setRol("ADMIN");
        repo.save(u);
      }
    };
  }
}
