package br.com.acme.application.ports.in;

import br.com.acme.application.domain.model.TransactionDomain;

public interface IPerformTransactionUseCase {
    TransactionDomain createTransaction(TransactionDomain transactionDomain);
}
