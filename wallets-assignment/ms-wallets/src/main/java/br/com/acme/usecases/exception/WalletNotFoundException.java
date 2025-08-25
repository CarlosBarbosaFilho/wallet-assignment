package br.com.acme.usecases.exception;

public class WalletNotFoundException extends  RuntimeException{
    public WalletNotFoundException(String message) {
        super(message);
    }
}