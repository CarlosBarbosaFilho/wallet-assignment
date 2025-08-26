package br.com.acme.application.ports.in;

import java.math.BigDecimal;

public interface IPerformDepositWalletUseCase {
    String performDeposit(String walletNumber, BigDecimal amount);
}
