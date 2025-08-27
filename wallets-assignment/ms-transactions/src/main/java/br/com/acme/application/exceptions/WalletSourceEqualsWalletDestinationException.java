package br.com.acme.application.exceptions;

public class WalletSourceEqualsWalletDestinationException extends  RuntimeException{
    public WalletSourceEqualsWalletDestinationException(String message) {
        super(message);
    }
}
