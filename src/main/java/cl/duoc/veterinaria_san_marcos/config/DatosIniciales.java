package cl.duoc.veterinaria_san_marcos.config;

import cl.duoc.veterinaria_san_marcos.model.Servicio;
import cl.duoc.veterinaria_san_marcos.repository.ServicioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

// Carga algunos servicios del catálogo la primera vez, para poder solicitar horas
@Configuration
public class DatosIniciales {
  @Bean CommandLineRunner serviciosIniciales(ServicioRepository repo) {
    return args -> {
      if (repo.count() > 0) return;
      repo.save(servicio("SV001", "Consulta general", "Consultas", "Perro / Gato", "30 min", 15000));
      repo.save(servicio("SV002", "Consulta urgencia", "Consultas", "Perro / Gato", "30 min", 25000));
      repo.save(servicio("VA001", "Vacuna antirrábica canina", "Vacunación", "Perro", "10 min", 12000));
      repo.save(servicio("VA003", "Vacuna bivalente felina", "Vacunación", "Gato", "10 min", 15000));
      repo.save(servicio("CI002", "Esterilización macho canino", "Cirugía", "Perro", "60 min", 60000));
    };
  }

  private Servicio servicio(String codigo, String nombre, String categoria,
      String especie, String duracion, int precio) {
    Servicio s = new Servicio();
    s.setCodigo(codigo);
    s.setNombre(nombre);
    s.setCategoria(categoria);
    s.setEspecie(especie);
    s.setDuracion(duracion);
    s.setPrecio(precio);
    return s;
  }
}