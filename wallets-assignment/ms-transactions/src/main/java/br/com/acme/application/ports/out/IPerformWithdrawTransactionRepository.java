package br.com.acme.application.ports.out;

import br.com.acme.adapters.output.client.requests.WithdrawRequest;

import java.math.BigDecimal;

public interface IPerformWithdrawTransactionRepository {

    Boolean performWithdraw(WithdrawRequest withdrawRequest);
}
