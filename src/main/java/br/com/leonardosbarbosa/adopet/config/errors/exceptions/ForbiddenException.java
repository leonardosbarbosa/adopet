package br.com.leonardosbarbosa.adopet.config.errors.exceptions;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message) {
        super(message);
    }
}
