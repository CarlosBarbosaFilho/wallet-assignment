package br.com.acme.infrastructure.external.consumer;

import br.com.acme.infrastructure.external.consumer.response.TransactionDomain;

public interface ListenTransactionQueueSuccess {

    TransactionDomain receivedTransaction(String message) throws Exception;
}
