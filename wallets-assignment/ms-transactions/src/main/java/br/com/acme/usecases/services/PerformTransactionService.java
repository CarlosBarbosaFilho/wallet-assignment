package br.com.acme.usecases.services;

import br.com.acme.domain.StatusTransaction;
import br.com.acme.domain.model.BalanceStatus;
import br.com.acme.domain.model.TransactionDomain;
import br.com.acme.repository.TransactionRepository;
import br.com.acme.usecases.*;
import br.com.acme.usecases.exceptions.InsufficientBalanceException;
import br.com.acme.usecases.external.sns.INotificationTransactionsService;
import br.com.acme.usecases.external.sync.CheckWalletBalanceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PerformTransactionService implements IPerformTransactionService {

    private final TransactionRepository transactionRepository;

    private final INotificationTransactionsService notificationService;
    private final CheckWalletBalanceClient walletBalanceClient;

    private final IPerformTransferTransactionService performTransferTransactionService;
    private final IPerformWithdrawTransactionService performWithdrawTransactionService;
    private final IPerformDepositTransactionService performDepositTransactionService;

    @Override
    public TransactionDomain createTransaction(TransactionDomain transactionDomain) {

        transactionDomain.setStatusTransaction(StatusTransaction.PENDING);
        transactionDomain.setCreatedAt(LocalDateTime.now().withNano(0));

        switch (transactionDomain.getTypeTransaction()) {
            case DEPOSIT -> handleDeposit(transactionDomain);
            case WITHDRAW -> handleWithdraw(transactionDomain);
            case TRANSFER -> handleTransfer(transactionDomain);
            default -> throw new IllegalArgumentException("Invalid transaction type");
        }

        return transactionDomain;
    }

    private void handleDeposit(TransactionDomain transactionDomain) {
        starterTransaction(transactionDomain);

        var pendingEntity = transactionRepository.save(TransactionDomain.toEntityPending(transactionDomain));
        transactionDomain.setCodeTransaction(pendingEntity.getCodeTransaction());
        notificationService.sendNotificationTransaction(transactionDomain);

        performDepositTransactionService.performDeposit(
                pendingEntity.getDestinationWallet(),
                pendingEntity.getAmountTransaction()
        );
        completeTransaction(transactionDomain);
    }

    private void handleWithdraw(TransactionDomain transactionDomain) {
        validateBalance(transactionDomain);
        starterTransaction(transactionDomain);

        var pendingEntity = transactionRepository.save(TransactionDomain.toEntityPending(transactionDomain));
        transactionDomain.setCodeTransaction(pendingEntity.getCodeTransaction());
        notificationService.sendNotificationTransaction(transactionDomain);

        performWithdrawTransactionService.performWithdraw(
                transactionDomain.getSourceWallet(),
                transactionDomain.getAmountTransaction()
        );
        completeTransaction(transactionDomain);
    }

    private void handleTransfer(TransactionDomain transactionDomain) {
        validateBalance(transactionDomain);
        starterTransaction(transactionDomain);

        var pendingEntity = transactionRepository.save(TransactionDomain.toEntityPending(transactionDomain));
        transactionDomain.setCodeTransaction(pendingEntity.getCodeTransaction());

        notificationService.sendNotificationTransaction(transactionDomain);
        performTransferTransactionService.performTransfer(
                transactionDomain.getSourceWallet(),
                transactionDomain.getDestinationWallet(),
                transactionDomain.getAmountTransaction()
        );
        completeTransaction(transactionDomain);
    }

    private void validateBalance(TransactionDomain transactionDomain) {
        var balance = walletBalanceClient.checkWalletBalance(transactionDomain.getSourceWallet());
        if (transactionDomain.hasNoBalance(balance).equals(BalanceStatus.NO_BALANCE)) {
            throw new InsufficientBalanceException("Insufficient balance to make the transfer");

        }else if (transactionDomain.hasNoBalance(balance).equals(BalanceStatus.INSUFFICIENT_BALANCE)){
            throw new InsufficientBalanceException("There is a balance but it is not enough to make the transfer");
        }
    }

    private void failedTransaction(TransactionDomain transactionDomain) {
        var filedEntity = transactionRepository.save(TransactionDomain.toEntityFailed(transactionDomain));
        var filedDomain = TransactionDomain.fromEntity(filedEntity);
        notificationService.sendNotificationTransaction(filedDomain);

        transactionDomain.setStatusTransaction(filedDomain.getStatusTransaction());
        transactionDomain.setCodeTransaction(filedDomain.getCodeTransaction());
    }

    private void completeTransaction(TransactionDomain transactionDomain) {
        var actualBalanceWalletSource = this.walletBalanceClient.checkWalletBalance(transactionDomain.getSourceWallet());
        var actualBalanceWalletDestination = this.walletBalanceClient.checkWalletBalance(transactionDomain.getDestinationWallet());

        transactionDomain.setCurrentBalanceSourceWallet(actualBalanceWalletSource);
        transactionDomain.setCurrentBalanceDestinationWallet(actualBalanceWalletDestination);

        var completedEntity = transactionRepository.save(TransactionDomain.toEntityCompleted(transactionDomain));
        var completedDomain = TransactionDomain.fromEntity(completedEntity);
        notificationService.sendNotificationTransaction(completedDomain);

        transactionDomain.setStatusTransaction(completedDomain.getStatusTransaction());
        transactionDomain.setCodeTransaction(completedDomain.getCodeTransaction());
    }

    private void starterTransaction(TransactionDomain transactionDomain) {
        var actualBalanceWalletSource = this.walletBalanceClient.checkWalletBalance(transactionDomain.getSourceWallet());
        var actualBalanceWalletDestination = this.walletBalanceClient.checkWalletBalance(transactionDomain.getDestinationWallet());
        transactionDomain.setCurrentBalanceSourceWallet(actualBalanceWalletSource);
        transactionDomain.setCurrentBalanceDestinationWallet(actualBalanceWalletDestination);

    }
}
