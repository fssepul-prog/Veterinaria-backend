package cl.duoc.veterinaria_san_marcos.repository;

import cl.duoc.veterinaria_san_marcos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}