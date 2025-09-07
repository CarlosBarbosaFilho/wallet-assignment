package br.com.acme.application.ports.out;

import java.math.BigDecimal;

public interface ICheckCurrentBalanceDestinationRepository {
    BigDecimal checkBalanceWalletDestination(String walletNumber);
}
