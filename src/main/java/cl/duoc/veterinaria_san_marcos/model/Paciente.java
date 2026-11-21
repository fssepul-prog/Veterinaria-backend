package cl.duoc.veterinaria_san_marcos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pacientes")
@Getter @Setter
public class Paciente {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String nombre;

  @NotBlank
  @Column(nullable = false)
  private String especie;

  @NotBlank
  @Column(nullable = false)
  private String nombreDueno;

  // Une la mascota con la cuenta del dueño en el microservicio de autenticación
  @NotBlank @Email
  @Column(nullable = false)
  private String correoDueno;

  // Se asigna al registrar; permite contar pacientes nuevos por período
  private LocalDate fechaRegistro;
}