package br.com.acme.application.ports.in;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IGetBalanceWalletInstantPassUseCase {

   BigDecimal getBalanceByWalletAndDate(String walletNumber, LocalDateTime datePass);
}
