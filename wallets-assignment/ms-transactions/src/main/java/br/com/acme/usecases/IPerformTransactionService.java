package br.com.acme.usecases;

import br.com.acme.domain.model.TransactionDomain;

public interface IPerformTransactionService {
    TransactionDomain createTransaction(TransactionDomain transactionDomain);
}
