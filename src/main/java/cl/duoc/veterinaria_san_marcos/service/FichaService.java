package cl.duoc.veterinaria_san_marcos.service;

import cl.duoc.veterinaria_san_marcos.exception.RecursoNoEncontradoException;
import cl.duoc.veterinaria_san_marcos.model.Ficha;
import cl.duoc.veterinaria_san_marcos.repository.FichaRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FichaService {
  private final FichaRepository repository;
  private final PacienteService pacientes;

  public FichaService(FichaRepository repository, PacienteService pacientes) {
    this.repository = repository;
    this.pacientes = pacientes;
  }

  public List<Ficha> listar(Long pacienteId) {
    return pacienteId == null
      ? repository.findAll(Sort.by(Sort.Direction.DESC, "fecha"))
      : repository.findByPacienteIdOrderByFechaDesc(pacienteId);
  }

  // Historial de las mascotas cuyo dueño es el usuario del token
  public List<Ficha> listarDe(String correo) {
    return repository.findByPacienteCorreoDuenoIgnoreCaseOrderByFechaDesc(correo);
  }

  public Ficha crear(Ficha ficha) {
    ficha.setId(null);
    ficha.setPaciente(pacientes.buscar(ficha.getPaciente().getId()));
    return repository.save(ficha);
  }

  public Ficha actualizar(Long id, Ficha datos) {
    Ficha actual = repository.findById(id).orElseThrow(() ->
      new RecursoNoEncontradoException("Ficha no encontrada: " + id));
    actual.setPaciente(pacientes.buscar(datos.getPaciente().getId()));
    actual.setFecha(datos.getFecha());
    actual.setMotivo(datos.getMotivo());
    actual.setDiagnostico(datos.getDiagnostico());
    actual.setMedicamento(datos.getMedicamento());
    actual.setVacuna(datos.getVacuna());
    actual.setProximaVacuna(datos.getProximaVacuna());
    actual.setProximoControl(datos.getProximoControl());
    return repository.save(actual);
  }
}