package cl.duoc.veterinaria_san_marcos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "citas")
@Getter @Setter
public class Cita {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String mascota;

  @NotBlank
  @Column(nullable = false)
  private String especie;

  private String dueno;

  @Column(nullable = false)
  private String correoCliente;

  private String telefono;

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "servicio_id")
  private Servicio servicio;

  @NotNull @FutureOrPresent
  @Column(nullable = false)
  private LocalDate fecha;

  @NotNull
  @JsonFormat(pattern = "HH:mm")
  @Column(nullable = false)
  private LocalTime hora;

  @Column(length = 500)
  private String observaciones;

  // Pendiente, Confirmada, Reagendada o Cancelada
  @Column(nullable = false)
  private String estado;
}