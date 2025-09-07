package br.com.acme.application.ports.in;

import br.com.acme.application.domain.model.TransactionDomain;

public interface IPerformDepositUseCase {

    TransactionDomain deposit(TransactionDomain transactionDomain);
}
