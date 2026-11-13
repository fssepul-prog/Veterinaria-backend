package cl.duoc.veterinaria_san_marcos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ficha_clinica")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FichaClinica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String mascota;

    @Column(length = 50)
    private String especie;

    @Column(nullable = false, length = 100)
    private String dueno;

    @Column(length = 30)
    private String fecha;

    @Column(length = 30)
    private String ultimaAtencion;

    @Column(length = 255)
    private String motivo;

    @Column(length = 500)
    private String diagnostico;

    @Column(length = 150)
    private String medicamento;

    @Column(length = 200)
    private String vacuna;

    @Column(length = 30)
    private String proximoControl;
}