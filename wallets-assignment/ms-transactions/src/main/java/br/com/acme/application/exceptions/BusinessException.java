package br.com.acme.application.exceptions;
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

