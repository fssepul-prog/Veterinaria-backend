package cl.duoc.veterinaria_san_marcos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fichas")
@Getter @Setter
public class Ficha {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @ManyToOne(optional = false)
  @JoinColumn(name = "paciente_id")
  private Paciente paciente;

  @NotNull @PastOrPresent
  @Column(nullable = false)
  private LocalDate fecha;

  @NotBlank
  @Column(nullable = false)
  private String motivo;

  @NotBlank
  @Column(nullable = false, length = 1000)
  private String diagnostico;

  private String medicamento;
  private String vacuna;
  private LocalDate proximaVacuna;
  private LocalDate proximoControl;
}