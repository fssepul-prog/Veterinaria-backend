package cl.duoc.veterinaria_san_marcos.repository;

import cl.duoc.veterinaria_san_marcos.model.Paciente;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
  long countByFechaRegistroBetween(LocalDate desde, LocalDate hasta);
}