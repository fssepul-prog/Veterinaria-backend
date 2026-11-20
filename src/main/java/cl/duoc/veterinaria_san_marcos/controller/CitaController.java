package cl.duoc.veterinaria_san_marcos.controller;

import cl.duoc.veterinaria_san_marcos.dto.*;
import cl.duoc.veterinaria_san_marcos.model.Cita;
import cl.duoc.veterinaria_san_marcos.service.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/citas")
@Tag(name = "Citas", description = "Solicitud y gestión de horas")
public class CitaController {
  private final CitaService service;
  public CitaController(CitaService service) { this.service = service; }

  @Operation(summary = "Lista todas las citas (ADMIN, RECEPCIONISTA)")
  @GetMapping public List<Cita> listar() { return service.listar(); }

  @Operation(summary = "Lista las citas del dueño autenticado (CLIENTE)")
  @GetMapping("/mias")
  public List<Cita> mias(Authentication autenticacion) {
    return service.listarDe(autenticacion.getName());
  }

  @Operation(summary = "Solicita una hora; queda Pendiente (CLIENTE)")
  @PostMapping
  public ResponseEntity<Cita> crear(@Valid @RequestBody Cita cita, Authentication autenticacion) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(cita, autenticacion.getName()));
  }

  @Operation(summary = "Confirma o cancela una cita (cancelar solo ADMIN)")
  @PutMapping("/{id}/estado")
  public Cita cambiarEstado(@PathVariable Long id, @Valid @RequestBody EstadoRequest request,
      Authentication autenticacion) {
    boolean esAdmin = autenticacion.getAuthorities().stream()
      .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    return service.cambiarEstado(id, request.estado(), esAdmin);
  }

  @Operation(summary = "Propone otra fecha y hora; queda Reagendada")
  @PutMapping("/{id}/reagendar")
  public Cita reagendar(@PathVariable Long id, @Valid @RequestBody ReagendarRequest request) {
    return service.reagendar(id, request.fecha(), request.hora());
  }
}