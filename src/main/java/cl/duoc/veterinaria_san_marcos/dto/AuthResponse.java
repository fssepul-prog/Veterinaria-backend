package cl.duoc.veterinaria_san_marcos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String token;
    private String nombre;
    private String correo;
    private String rol;
}