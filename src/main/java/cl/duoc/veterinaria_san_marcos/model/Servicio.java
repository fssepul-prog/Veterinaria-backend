package cl.duoc.veterinaria_san_marcos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "servicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 60)
    private String categoria;

    @Column(length = 500)
    private String descripcion;

    @Column(nullable = false)
    private Integer precio;

    @Column(length = 60)
    private String especie;

    @Column(length = 40)
    private String duracion;

    @Column(length = 120)
    private String imagen;

    @Column(nullable = false)
    private Integer atenciones = 0;
}