package cl.duoc.veterinaria_san_marcos.repository;

import cl.duoc.veterinaria_san_marcos.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByEstado(String estado);
    List<Cita> findByDuenoIgnoreCase(String dueno);
    List<Cita> findByEmailIgnoreCase(String email);
}