package cl.duoc.veterinaria_san_marcos.controller;

import cl.duoc.veterinaria_san_marcos.dto.ReporteResponse;
import cl.duoc.veterinaria_san_marcos.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes", description = "Indicadores por período (solo ADMIN)")
public class ReporteController {
  private final ReporteService service;
  public ReporteController(ReporteService service) { this.service = service; }

  @Operation(summary = "Atenciones e ingresos por servicio y pacientes nuevos")
  @GetMapping("/atenciones")
  public ReporteResponse atenciones(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
    return service.atenciones(desde, hasta);
  }
}