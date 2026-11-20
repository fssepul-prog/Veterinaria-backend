package cl.duoc.veterinaria_san_marcos.repository;

import cl.duoc.veterinaria_san_marcos.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
  boolean existsByCodigoIgnoreCase(String codigo);
}