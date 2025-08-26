package br.com.acme.application.ports.in;

import java.math.BigDecimal;

public interface IPerformWithdrawWalletUseCase {
    String performWithdraw(String walletNumber, BigDecimal amount);
}
