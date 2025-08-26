<<<<<<<< HEAD:wallets-assignment/ms-transactions/src/main/java/br/com/acme/application/exceptions/BusinessException.java
package br.com.acme.application.exceptions;
========
package br.com.acme.application.exception;
>>>>>>>> 90222cb (feat: create hexagonal strucure to ms-wallets and configure the ms-notification):wallets-assignment/ms-wallets/src/main/java/br/com/acme/application/exception/BusinessException.java

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}

