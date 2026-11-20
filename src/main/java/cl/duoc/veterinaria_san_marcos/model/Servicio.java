package cl.duoc.veterinaria_san_marcos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "servicios")
@Getter @Setter
public class Servicio {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false, unique = true, length = 10)
  private String codigo;

  @NotBlank
  @Column(nullable = false)
  private String nombre;

  @NotBlank
  @Column(nullable = false)
  private String categoria;

  @Column(length = 500)
  private String descripcion;

  private String especie;
  private String duracion;

  @NotNull @PositiveOrZero
  @Column(nullable = false)
  private Integer precio;

  private String imagen;
}