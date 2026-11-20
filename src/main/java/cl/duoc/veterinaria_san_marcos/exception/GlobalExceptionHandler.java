package cl.duoc.veterinaria_san_marcos.exception;

import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(RecursoNoEncontradoException.class)
  ResponseEntity<Map<String, String>> noEncontrado(RecursoNoEncontradoException e) {
    return respuesta(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(ConflictoException.class)
  ResponseEntity<Map<String, String>> conflicto(ConflictoException e) {
    return respuesta(HttpStatus.CONFLICT, e.getMessage());
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  ResponseEntity<Map<String, String>> integridad() {
    return respuesta(HttpStatus.CONFLICT, "La operación afecta registros relacionados y no se puede completar");
  }

  @ExceptionHandler(AccessDeniedException.class)
  ResponseEntity<Map<String, String>> sinPermiso(AccessDeniedException e) {
    return respuesta(HttpStatus.FORBIDDEN, e.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<Map<String, String>> argumentoInvalido(IllegalArgumentException e) {
    return respuesta(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<Map<String, String>> datosInvalidos(MethodArgumentNotValidException e) {
    String mensaje = e.getBindingResult().getFieldErrors().stream()
      .findFirst().map(error -> error.getField() + ": " + error.getDefaultMessage())
      .orElse("Datos inválidos");
    return respuesta(HttpStatus.BAD_REQUEST, mensaje);
  }

  @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
  ResponseEntity<Map<String, String>> formatoInvalido() {
    return respuesta(HttpStatus.BAD_REQUEST, "Algún dato tiene un formato inválido (fechas AAAA-MM-DD, horas HH:mm)");
  }

  // El frontend muestra el texto que viene en "error"
  private ResponseEntity<Map<String, String>> respuesta(HttpStatus estado, String mensaje) {
    return ResponseEntity.status(estado).body(Map.of("error", mensaje));
  }
}