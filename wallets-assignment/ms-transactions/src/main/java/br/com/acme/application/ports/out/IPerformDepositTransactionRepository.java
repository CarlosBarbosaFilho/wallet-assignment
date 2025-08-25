package br.com.acme.application.ports.out;

import java.math.BigDecimal;

public interface IPerformDepositTransactionRepository {

    Boolean performDeposit(String walletNumber, BigDecimal amount);
}
