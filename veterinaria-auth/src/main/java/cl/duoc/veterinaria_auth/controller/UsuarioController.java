package cl.duoc.veterinaria_auth.controller;

import cl.duoc.veterinaria_auth.dto.*;
import cl.duoc.veterinaria_auth.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Gestión de usuarios y roles (solo ADMIN)")
public class UsuarioController {
  private final UsuarioService service;
  public UsuarioController(UsuarioService service) { this.service = service; }

  @Operation(summary = "Lista todos los usuarios")
  @GetMapping
  public List<UsuarioResponse> listar() { return service.listar(); }

  @Operation(summary = "Crea un usuario con el rol indicado")
  @PostMapping
  public ResponseEntity<UsuarioResponse> crear(@Valid @RequestBody UsuarioRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
  }

  @Operation(summary = "Cambia nombre, teléfono, rol o estado activo")
  @PutMapping("/{id}")
  public UsuarioResponse actualizar(@PathVariable Long id,
      @RequestBody UsuarioUpdateRequest cambios, Authentication autenticacion) {
    return service.actualizar(id, cambios, autenticacion.getName());
  }
}
