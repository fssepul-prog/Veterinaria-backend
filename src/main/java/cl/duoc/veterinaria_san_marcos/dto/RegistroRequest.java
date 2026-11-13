package cl.duoc.veterinaria_san_marcos.dto;

import lombok.Data;

@Data
public class RegistroRequest {
    private String nombre;
    private String correo;
    private String contrasena;
    private String telefono;
    private String rol;
}