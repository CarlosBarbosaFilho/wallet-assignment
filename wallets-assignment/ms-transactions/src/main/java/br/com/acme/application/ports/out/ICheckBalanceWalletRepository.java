package br.com.acme.application.ports.out;

import java.math.BigDecimal;

public interface ICheckBalanceWalletRepository {
    BigDecimal checkBalanceWallet(String walletNumber);
}
