package cl.duoc.veterinaria_san_marcos.controller;

import cl.duoc.veterinaria_san_marcos.model.Ficha;
import cl.duoc.veterinaria_san_marcos.service.FichaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fichas")
@Tag(name = "Fichas clínicas", description = "Historial clínico y de vacunas")
public class FichaController {
  private final FichaService service;
  public FichaController(FichaService service) { this.service = service; }

  @Operation(summary = "Lista fichas; filtra por paciente si se indica pacienteId")
  @GetMapping
  public List<Ficha> listar(@RequestParam(required = false) Long pacienteId) {
    return service.listar(pacienteId);
  }

  @Operation(summary = "Historial de las mascotas del dueño autenticado (CLIENTE)")
  @GetMapping("/mias")
  public List<Ficha> mias(Authentication autenticacion) {
    return service.listarDe(autenticacion.getName());
  }

  @Operation(summary = "Registra una atención")
  @PostMapping
  public ResponseEntity<Ficha> crear(@Valid @RequestBody Ficha ficha) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(ficha));
  }

  @Operation(summary = "Edita una atención")
  @PutMapping("/{id}")
  public Ficha actualizar(@PathVariable Long id, @Valid @RequestBody Ficha ficha) {
    return service.actualizar(id, ficha);
  }
}