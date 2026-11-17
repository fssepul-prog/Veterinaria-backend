package cl.duoc.veterinaria_auth.exception;

import java.util.Map;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(BadCredentialsException.class)
  ResponseEntity<Map<String, String>> credencialesInvalidas() {
    return respuesta(HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos");
  }

  @ExceptionHandler(DisabledException.class)
  ResponseEntity<Map<String, String>> cuentaDesactivada(DisabledException e) {
    return respuesta(HttpStatus.UNAUTHORIZED, e.getMessage());
  }

  @ExceptionHandler(RecursoNoEncontradoException.class)
  ResponseEntity<Map<String, String>> noEncontrado(RecursoNoEncontradoException e) {
    return respuesta(HttpStatus.NOT_FOUND, e.getMessage());
  }

  @ExceptionHandler(ConflictoException.class)
  ResponseEntity<Map<String, String>> conflicto(ConflictoException e) {
    return respuesta(HttpStatus.CONFLICT, e.getMessage());
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

  // El frontend muestra el texto que viene en "mensaje"
  private ResponseEntity<Map<String, String>> respuesta(HttpStatus estado, String mensaje) {
    return ResponseEntity.status(estado).body(Map.of("mensaje", mensaje));
  }
}
