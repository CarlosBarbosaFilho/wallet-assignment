package br.com.acme.usecases;

import java.math.BigDecimal;

public interface ICheckBalanceWalletService {
    BigDecimal checkBalanceWallet(String walletNumber);
}
