package cl.duoc.veterinaria_san_marcos.controller;

import cl.duoc.veterinaria_san_marcos.model.Paciente;
import cl.duoc.veterinaria_san_marcos.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pacientes")
@Tag(name = "Pacientes", description = "Mascotas registradas en la clínica")
public class PacienteController {
  private final PacienteService service;
  public PacienteController(PacienteService service) { this.service = service; }

  @Operation(summary = "Lista los pacientes")
  @GetMapping public List<Paciente> listar() { return service.listar(); }

  @Operation(summary = "Busca un paciente por id")
  @GetMapping("/{id}") public Paciente buscar(@PathVariable Long id) { return service.buscar(id); }

  @Operation(summary = "Registra un paciente")
  @PostMapping
  public ResponseEntity<Paciente> crear(@Valid @RequestBody Paciente paciente) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(paciente));
  }
}