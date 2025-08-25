package br.com.acme.usecases;

import java.math.BigDecimal;

public interface IPerformWithdrawWalletService {
    String performWithdraw(String walletNumber, BigDecimal amount);
}
