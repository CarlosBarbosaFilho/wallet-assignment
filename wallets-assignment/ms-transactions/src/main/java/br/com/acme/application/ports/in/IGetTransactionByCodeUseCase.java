package br.com.acme.application.ports.in;

import br.com.acme.application.domain.model.TransactionDomain;

import java.util.UUID;

public interface IGetTransactionByCodeUseCase {

    TransactionDomain transactionByCode(UUID codeTransaction);
}
