package br.com.acme.application.ports.in;

import java.math.BigDecimal;

public interface ICheckBalanceWalletUseCase {

    BigDecimal getBalance(String walletNumber);
}
