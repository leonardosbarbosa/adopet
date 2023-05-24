package br.com.leonardosbarbosa.adopet.config.errors.exceptions;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
}
