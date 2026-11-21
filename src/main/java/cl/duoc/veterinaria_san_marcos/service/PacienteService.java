package cl.duoc.veterinaria_san_marcos.service;

import cl.duoc.veterinaria_san_marcos.exception.RecursoNoEncontradoException;
import cl.duoc.veterinaria_san_marcos.model.Paciente;
import cl.duoc.veterinaria_san_marcos.repository.PacienteRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {
  private final PacienteRepository repository;

  public PacienteService(PacienteRepository repository) {
    this.repository = repository;
  }

  public List<Paciente> listar() { return repository.findAll(); }

  public Paciente buscar(Long id) {
    return repository.findById(id).orElseThrow(() ->
      new RecursoNoEncontradoException("Paciente no encontrado: " + id));
  }

  public Paciente crear(Paciente paciente) {
    paciente.setId(null);
    paciente.setCorreoDueno(paciente.getCorreoDueno().trim().toLowerCase());
    paciente.setFechaRegistro(LocalDate.now());
    return repository.save(paciente);
  }
}