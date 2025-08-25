package br.com.acme.adapters.output.sns;

import br.com.acme.application.domain.model.TransactionDomain;

public interface NotificationTransactionsClient {

    void sendNotificationTransaction(TransactionDomain transactionDomain);
}
