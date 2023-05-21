package br.com.leonardosbarbosa.adopet.config.errors.exceptions;

public class DuplicatedEmailException extends RuntimeException {

    public DuplicatedEmailException(String message) {
        super(message);
    }
}
