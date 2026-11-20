package cl.duoc.veterinaria_san_marcos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReagendarRequest(
  @NotNull @FutureOrPresent LocalDate fecha,
  @NotNull @JsonFormat(pattern = "HH:mm") LocalTime hora
) {}