package br.com.acme.controller.api;

import br.com.acme.controller.request.TransactionRequest;
import br.com.acme.controller.response.BalanceWalletInstant;
import br.com.acme.controller.response.BalanceWalletResponse;
import br.com.acme.controller.response.TransactionConfirmedResponse;
import br.com.acme.controller.response.TransactionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionResource {

    TransactionConfirmedResponse performTransaction(TransactionRequest request);

    BalanceWalletInstant balanceWalletPass(String walletNumber, LocalDateTime datePass);

    BalanceWalletResponse checkBalance(String wallerNumber);

    List<TransactionResponse> transactionByPeriod(String walletNumber, LocalDateTime startDate, LocalDateTime endDate);
}
