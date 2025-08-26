package br.com.acme.application.exception;
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

