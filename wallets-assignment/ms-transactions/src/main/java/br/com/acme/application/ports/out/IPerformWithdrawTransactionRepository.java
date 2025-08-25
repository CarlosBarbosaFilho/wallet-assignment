package br.com.acme.application.ports.out;

import java.math.BigDecimal;

public interface IPerformWithdrawTransactionRepository {

    Boolean performWithdraw(String walletNumber, BigDecimal amount);
}
