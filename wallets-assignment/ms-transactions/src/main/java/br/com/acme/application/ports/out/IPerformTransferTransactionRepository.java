package br.com.acme.application.ports.out;

import br.com.acme.adapters.output.client.requests.TransferRequest;

public interface IPerformTransferTransactionRepository {

    Boolean performTransfer(TransferRequest transferRequest);
}
