package cl.duoc.veterinaria_san_marcos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cita")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String mascota;

    @Column(length = 50)
    private String especie;

    @Column(nullable = false, length = 100)
    private String dueno;

    @Column(length = 120)
    private String email;

    @Column(length = 30)
    private String telefono;

    @Column(nullable = false, length = 120)
    private String servicio;

    @Column(length = 30)
    private String fecha;

    @Column(nullable = false, length = 30)
    private String estado = "Pendiente";

    @Column(length = 500)
    private String mensaje;
}