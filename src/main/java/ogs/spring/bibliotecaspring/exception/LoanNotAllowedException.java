package ogs.spring.bibliotecaspring.exception;

public class LoanNotAllowedException extends RuntimeException {
    public LoanNotAllowedException() {
        super("No se puede realizar el préstamo ya que el usuario no cumple las condiciones requeridas.");
    }
}
