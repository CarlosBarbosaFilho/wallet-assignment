package br.com.acme.usecases;

import br.com.acme.domain.model.TransactionDomain;

import java.util.UUID;

public interface IGetTransactionByCodeService {

    TransactionDomain transactionByCode(UUID codeTransaction);
}
