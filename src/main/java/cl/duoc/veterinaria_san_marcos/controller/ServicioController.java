package cl.duoc.veterinaria_san_marcos.controller;

import cl.duoc.veterinaria_san_marcos.model.Servicio;
import cl.duoc.veterinaria_san_marcos.service.ServicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/servicios")
@Tag(name = "Servicios", description = "Catálogo de servicios de la clínica")
public class ServicioController {

  private final ServicioService service;
  public ServicioController(ServicioService service) { this.service = service; }


  @Operation(summary = "Lista el catálogo")
  @GetMapping public List<Servicio> listar() { return service.listar(); }
  @Operation(summary = "Busca un servicio por id")
  @GetMapping("/{id}") public Servicio buscar(@PathVariable Long id) { return service.buscar(id); }
  @Operation(summary = "Crea un servicio (ADMIN)")
  @PostMapping


  public ResponseEntity<Servicio> crear(@Valid @RequestBody Servicio servicio) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(servicio));
  }


  @Operation(summary = "Actualiza un servicio (ADMIN)")
  @PutMapping("/{id}")
  public Servicio actualizar(@PathVariable Long id, @Valid @RequestBody Servicio servicio) {
    return service.actualizar(id, servicio);
  }

  
  @Operation(summary = "Elimina un servicio (ADMIN)")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void eliminar(@PathVariable Long id) { service.eliminar(id); }
}
