package cl.duoc.veterinaria_san_marcos.repository;

import cl.duoc.veterinaria_san_marcos.model.Cita;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {
  List<Cita> findByCorreoClienteIgnoreCaseOrderByFechaAscHoraAsc(String correo);
  List<Cita> findByEstadoAndFechaBetween(String estado, LocalDate desde, LocalDate hasta);
}