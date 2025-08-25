package br.com.acme.application.ports.in;

import br.com.acme.application.domain.model.TransactionDomain;

import java.time.LocalDateTime;
import java.util.List;

public interface IGetTransactionsByPeriodUseCase {
    List<TransactionDomain> getTransactionsPeriod(String walletNumber,   LocalDateTime startDate,
                                                  LocalDateTime endDate);
}
