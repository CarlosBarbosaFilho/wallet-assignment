package br.com.acme.usecases.exceptions;

public class InsufficientBalanceException extends  RuntimeException{
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
