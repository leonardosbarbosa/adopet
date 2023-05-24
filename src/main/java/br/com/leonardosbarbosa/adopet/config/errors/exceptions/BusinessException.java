package br.com.leonardosbarbosa.adopet.config.errors.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
