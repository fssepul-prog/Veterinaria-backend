package cl.duoc.veterinaria_san_marcos.dto;


import jakarta.validation.constraints.NotBlank;

public record EstadoRequest(@NotBlank String estado) {}