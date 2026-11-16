package cl.duoc.veterinaria_san_marcos.service;


import cl.duoc.veterinaria_san_marcos.exception.*;
import cl.duoc.veterinaria_san_marcos.model.Servicio;
import cl.duoc.veterinaria_san_marcos.repository.ServicioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ServicioService {

  private final ServicioRepository repository;

  public ServicioService(ServicioRepository repository) {
    this.repository = repository;
  }

  public List<Servicio> listar() {
    return repository.findAll();
  }

  public Servicio buscar(Long id) {
    return repository.findById(id).orElseThrow(() ->
      new RecursoNoEncontradoException("Servicio no encontrado: " + id));
  }

  public Servicio crear(Servicio servicio) {
    if (repository.existsByCodigoIgnoreCase(servicio.getCodigo())) {
      throw new ConflictoException("Ya existe un servicio con el código " + servicio.getCodigo());
    }
    
    servicio.setId(null);
    return repository.save(servicio);
  }

  public Servicio actualizar(Long id, Servicio datos) {
    Servicio actual = buscar(id);
    actual.setCodigo(datos.getCodigo());
    actual.setNombre(datos.getNombre());
    actual.setCategoria(datos.getCategoria());
    actual.setDescripcion(datos.getDescripcion());
    actual.setEspecie(datos.getEspecie());
    actual.setDuracion(datos.getDuracion());
    actual.setPrecio(datos.getPrecio());
    actual.setImagen(datos.getImagen());
    return repository.save(actual);
  }

  public void eliminar(Long id) {
    repository.delete(buscar(id));
  }
}