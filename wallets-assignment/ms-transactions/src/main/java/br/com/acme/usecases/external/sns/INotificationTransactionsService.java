package br.com.acme.usecases.external.sns;

import br.com.acme.domain.model.TransactionDomain;

public interface INotificationTransactionsService {

    void sendNotificationTransaction(TransactionDomain transactionDomain);
}
