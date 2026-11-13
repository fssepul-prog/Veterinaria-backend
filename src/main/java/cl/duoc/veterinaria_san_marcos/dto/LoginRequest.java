package cl.duoc.veterinaria_san_marcos.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String contrasena;
}