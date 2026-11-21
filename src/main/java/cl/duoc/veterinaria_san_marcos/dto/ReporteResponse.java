package cl.duoc.veterinaria_san_marcos.dto;

import java.util.List;

public record ReporteResponse(List<FilaReporte> porServicio, long pacientesNuevos) {}