package br.com.acme.application.ports.out;

import br.com.acme.adapters.output.client.requests.DepositRequest;

import java.math.BigDecimal;

public interface IPerformDepositTransactionRepository {

    Boolean performDeposit(DepositRequest depositRequest);
}
