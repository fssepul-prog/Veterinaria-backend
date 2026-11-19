package cl.duoc.veterinaria_auth.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter @Setter
public class Usuario {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false, unique = true)
  private String correo;

  private String telefono;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String rol;

  @Column(nullable = false)
  private boolean activo = true;
}
