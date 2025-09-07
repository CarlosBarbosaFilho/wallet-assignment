package br.com.acme.application.usecases.service;

import br.com.acme.adapters.output.database.repository.TransactionRepository;
import br.com.acme.adapters.output.database.entity.TransactionEntity;
import br.com.acme.application.exceptions.BusinessException;
import br.com.acme.application.ports.in.IGetBalanceWalletInstantPassUseCase;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@UseCase
@AllArgsConstructor
public class BalanceWalletInstantPassUseCase implements IGetBalanceWalletInstantPassUseCase {

    private final TransactionRepository transactionRepository;

    public BigDecimal getBalanceByWalletAndDate(String walletNumber, LocalDateTime date) {

        if (date.isAfter(LocalDateTime.now())) {
            throw new BusinessException("The date entered is in the future. The balance cannot be calculated.");
        }
        List<TransactionEntity> transactions = transactionRepository
                .findLastCompletedTransactionUntilDate(walletNumber, date);

        if (transactions.isEmpty()) {
            return BigDecimal.ZERO;
        }

        TransactionEntity lastTransaction = transactions.get(0);
        if (walletNumber.equals(lastTransaction.getSourceWallet())) {
            return lastTransaction.getCurrentBalanceSourceWallet();
        }
        return lastTransaction.getCurrentBalanceDestinationWallet();
    }
}
