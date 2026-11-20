package cl.duoc.veterinaria_san_marcos.service;

import cl.duoc.veterinaria_san_marcos.exception.RecursoNoEncontradoException;
import cl.duoc.veterinaria_san_marcos.model.Cita;
import cl.duoc.veterinaria_san_marcos.repository.CitaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class CitaService {
  private static final Set<String> ESTADOS_PERMITIDOS = Set.of("Confirmada", "Cancelada");

  private final CitaRepository repository;
  private final ServicioService servicios;

  public CitaService(CitaRepository repository, ServicioService servicios) {
    this.repository = repository;
    this.servicios = servicios;
  }

  public List<Cita> listar() {
    return repository.findAll(Sort.by("fecha", "hora"));
  }

  public List<Cita> listarDe(String correo) {
    return repository.findByCorreoClienteIgnoreCaseOrderByFechaAscHoraAsc(correo);
  }

  // El correo sale del token: un cliente no puede pedir horas a nombre de otro
  public Cita crear(Cita cita, String correoCliente) {
    if (cita.getServicio().getId() == null) {
      throw new IllegalArgumentException("Debes indicar el servicio");
    }
    cita.setId(null);
    cita.setServicio(servicios.buscar(cita.getServicio().getId()));
    cita.setCorreoCliente(correoCliente);
    cita.setEstado("Pendiente");
    return repository.save(cita);
  }

  public Cita cambiarEstado(Long id, String estado, boolean esAdmin) {
    if (!ESTADOS_PERMITIDOS.contains(estado)) {
      throw new IllegalArgumentException("Estado no válido: " + estado);
    }
    if (estado.equals("Cancelada") && !esAdmin) {
      throw new AccessDeniedException("Solo el administrador puede cancelar citas");
    }
    Cita cita = abierta(id);
    cita.setEstado(estado);
    return repository.save(cita);
  }

  public Cita reagendar(Long id, LocalDate fecha, LocalTime hora) {
    Cita cita = abierta(id);
    cita.setFecha(fecha);
    cita.setHora(hora);
    cita.setEstado("Reagendada");
    return repository.save(cita);
  }

  // Una cita cancelada ya no se puede modificar
  private Cita abierta(Long id) {
    Cita cita = repository.findById(id).orElseThrow(() ->
      new RecursoNoEncontradoException("Cita no encontrada: " + id));
    if (cita.getEstado().equals("Cancelada")) {
      throw new IllegalArgumentException("La cita está cancelada");
    }
    return cita;
  }
}