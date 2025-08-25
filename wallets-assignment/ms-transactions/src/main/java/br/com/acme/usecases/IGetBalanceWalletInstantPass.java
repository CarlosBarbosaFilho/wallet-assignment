package br.com.acme.usecases;

import br.com.acme.domain.model.TransactionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface IGetBalanceWalletInstantPass {

   BigDecimal getBalanceByWalletAndDate(String walletNumber, LocalDateTime datePass);
}
