package br.com.acme.usecases;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ICheckBalanceWalletByDatePassService {

    BigDecimal getBalance(String walletNumber, LocalDateTime datePass);
}
