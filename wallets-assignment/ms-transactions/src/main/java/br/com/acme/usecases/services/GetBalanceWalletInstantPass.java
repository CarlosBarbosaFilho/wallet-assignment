package br.com.acme.usecases.services;

import br.com.acme.domain.StatusTransaction;
import br.com.acme.domain.model.TransactionEntity;
import br.com.acme.repository.TransactionRepository;
import br.com.acme.usecases.IGetBalanceWalletInstantPass;
import br.com.acme.usecases.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GetBalanceWalletInstantPass implements IGetBalanceWalletInstantPass {

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
