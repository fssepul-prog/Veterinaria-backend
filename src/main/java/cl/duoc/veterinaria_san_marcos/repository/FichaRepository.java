package cl.duoc.veterinaria_san_marcos.repository;

import cl.duoc.veterinaria_san_marcos.model.Ficha;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FichaRepository extends JpaRepository<Ficha, Long> {
  List<Ficha> findByPacienteIdOrderByFechaDesc(Long pacienteId);
  List<Ficha> findByPacienteCorreoDuenoIgnoreCaseOrderByFechaDesc(String correo);
}