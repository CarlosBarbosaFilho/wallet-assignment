package br.com.acme.usecases;

import java.math.BigDecimal;

public interface IPerformDepositWalletService {
    String performDeposit(String walletNumber, BigDecimal amount);
}
