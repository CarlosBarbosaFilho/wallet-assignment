package br.com.acme.application.ports.out;

import java.math.BigDecimal;

public interface IPerformTransferTransactionRepository {

    Boolean performTransfer(String walletSource, String walletDestination, BigDecimal amount);
}
