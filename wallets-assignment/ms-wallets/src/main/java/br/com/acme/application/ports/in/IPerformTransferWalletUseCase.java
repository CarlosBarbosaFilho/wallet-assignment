package br.com.acme.application.ports.in;

import java.math.BigDecimal;

public interface IPerformTransferWalletUseCase {

    String performTransfer(String walletSource, String walletDestination, BigDecimal amount);
}
