package br.com.acme.adapters.input.web.api;

import br.com.acme.adapters.input.web.request.TransactionDeposit;
import br.com.acme.adapters.input.web.request.TransactionTransfer;
import br.com.acme.adapters.input.web.request.TransactionWithdraw;
import br.com.acme.adapters.input.web.response.BalanceWalletInstant;
import br.com.acme.adapters.input.web.response.BalanceWalletResponse;
import br.com.acme.adapters.input.web.response.TransactionConfirmedResponse;
import br.com.acme.adapters.input.web.response.TransactionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionResource {

    TransactionConfirmedResponse performTransfer(TransactionTransfer request);

    TransactionConfirmedResponse performDeposit(TransactionDeposit request);

    TransactionConfirmedResponse performWithdraw(TransactionWithdraw request);

    BalanceWalletInstant balanceWalletPass(String walletNumber, LocalDateTime datePass);

    BalanceWalletResponse checkBalance(String wallerNumber);

    List<TransactionResponse> transactionByPeriod(String walletNumber, LocalDateTime startDate, LocalDateTime endDate);
}
