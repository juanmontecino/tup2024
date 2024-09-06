package ar.edu.utn.frbb.tup.model.exception;

public class TipoCuentaNoSoportadaException extends RuntimeException {
    public TipoCuentaNoSoportadaException(String message) {
        super(message);
    }
}