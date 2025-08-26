package br.com.acme.application.ports.out;

import br.com.acme.application.domain.model.TransactionDomain;

public interface IProducerEventsWalletTransaction {
     void sendEventWalletTransaction(TransactionDomain transactionDomain);
}
