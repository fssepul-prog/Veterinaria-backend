package cl.duoc.veterinaria_san_marcos.service;

import cl.duoc.veterinaria_san_marcos.dto.*;
import cl.duoc.veterinaria_san_marcos.model.Cita;
import cl.duoc.veterinaria_san_marcos.repository.*;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ReporteService {
  private final CitaRepository citas;
  private final PacienteRepository pacientes;

  public ReporteService(CitaRepository citas, PacienteRepository pacientes) {
    this.citas = citas;
    this.pacientes = pacientes;
  }

  // Cuenta como atención cada cita confirmada dentro del período
  public ReporteResponse atenciones(LocalDate desde, LocalDate hasta) {
    if (desde.isAfter(hasta)) {
      throw new IllegalArgumentException("La fecha inicial debe ser anterior o igual a la final");
    }
    var porServicio = citas.findByEstadoAndFechaBetween("Confirmada", desde, hasta).stream()
      .collect(Collectors.groupingBy(c -> c.getServicio().getNombre(), TreeMap::new, Collectors.toList()));

    List<FilaReporte> filas = porServicio.entrySet().stream()
      .map(e -> new FilaReporte(e.getKey(), e.getValue().size(), total(e.getValue())))
      .toList();
    return new ReporteResponse(filas, pacientes.countByFechaRegistroBetween(desde, hasta));
  }

  private long total(List<Cita> lista) {
    return lista.stream().mapToLong(c -> c.getServicio().getPrecio()).sum();
  }
}