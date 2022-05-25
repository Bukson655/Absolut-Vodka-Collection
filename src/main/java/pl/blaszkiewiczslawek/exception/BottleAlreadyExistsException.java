package pl.blaszkiewiczslawek.exception;

public class BottleAlreadyExistsException extends RuntimeException {

    public BottleAlreadyExistsException(String message) {
        super(message);
    }
}
