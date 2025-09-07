package br.com.acme.application.ports.out;

import java.math.BigDecimal;

public interface ICheckCurrentBalanceSourceRepository {
    BigDecimal checkBalanceWalletSource(String walletNumber);
}
