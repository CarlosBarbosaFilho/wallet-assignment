package br.com.acme.usecases;

import java.math.BigDecimal;

public interface IPerformTransferWalletService {

    String performTransfer(String walletSource, String walletDestination, BigDecimal amount);
}
