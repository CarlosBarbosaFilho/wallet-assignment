package br.com.acme.application.exceptions;

public class WalletDestinationEqualsWalletSource extends  RuntimeException{
    public WalletDestinationEqualsWalletSource(String message) {
        super(message);
    }
}
