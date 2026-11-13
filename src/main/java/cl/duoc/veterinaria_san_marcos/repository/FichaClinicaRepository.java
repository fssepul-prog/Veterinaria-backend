package cl.duoc.veterinaria_san_marcos.repository;

import cl.duoc.veterinaria_san_marcos.model.FichaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FichaClinicaRepository extends JpaRepository<FichaClinica, Long> {
    List<FichaClinica> findByEspecieIgnoreCase(String especie);
    List<FichaClinica> findByDuenoIgnoreCase(String dueno);
}