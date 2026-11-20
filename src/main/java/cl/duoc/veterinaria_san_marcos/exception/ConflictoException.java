package cl.duoc.veterinaria_san_marcos.exception;

public class ConflictoException extends RuntimeException {
  public ConflictoException(String mensaje) {
    super(mensaje);
  }
}