package br.com.acme.application.exceptions;

public class FailedPerformedTransactionException extends  RuntimeException{
    public FailedPerformedTransactionException(String message) {
        super(message);
    }
}
