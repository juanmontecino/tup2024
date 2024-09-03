package ar.edu.utn.frbb.tup.model.exception;

public class ClienteAlreadyExistsException extends RuntimeException {
    public ClienteAlreadyExistsException(long dni) {
        super("Ya existe un cliente con DNI " + dni);
    }
}
