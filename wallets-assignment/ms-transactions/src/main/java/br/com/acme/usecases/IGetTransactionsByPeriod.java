package br.com.acme.usecases;

import br.com.acme.domain.model.TransactionDomain;

import java.time.LocalDateTime;
import java.util.List;

public interface IGetTransactionsByPeriod {
    List<TransactionDomain> getTransactionsPeriod(String walletNumber,   LocalDateTime startDate,
                                                  LocalDateTime endDate);
}
