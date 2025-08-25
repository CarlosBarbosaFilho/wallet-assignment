package br.com.acme.usecases;

import java.math.BigDecimal;

public interface IPerformTransferTransactionService {

    Boolean performTransfer(String walletSource, String walletDestination, BigDecimal amount);
}
