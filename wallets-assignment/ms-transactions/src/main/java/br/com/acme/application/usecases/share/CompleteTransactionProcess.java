package br.com.acme.application.usecases.share;

import br.com.acme.adapters.output.database.repository.TransactionRepository;
import br.com.acme.adapters.output.sns.NotificationTransactionsClient;
import br.com.acme.application.domain.model.TransactionDomain;
import br.com.acme.application.ports.out.ICheckBalanceWalletRepository;
import br.com.acme.application.ports.out.ICheckCurrentBalanceDestinationRepository;
import br.com.acme.application.ports.out.ICheckCurrentBalanceSourceRepository;
import br.com.acme.application.ports.out.IProducerEventsWalletTransaction;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@UseCase
@AllArgsConstructor
public class CompleteTransactionProcess {

    private final TransactionRepository transactionRepository;
    private final NotificationTransactionsClient notificationService;
    private final IProducerEventsWalletTransaction producerEventsWalletTransaction;
    private final ICheckCurrentBalanceDestinationRepository checkCurrentBalanceDestinationRepository;
    private final ICheckCurrentBalanceSourceRepository checkCurrentBalanceSourceRepository;
    private final ICheckBalanceWalletRepository checkBalanceWalletRepository;

    public void completeTransaction(TransactionDomain transactionDomain) {
        updateBalancesWallets(transactionDomain);
        var completedDomain = createTransaction(transactionDomain);
        notifyAndPublishTransaction(completedDomain);
        syncingTransactionStatus(transactionDomain, completedDomain);
    }

    private void updateBalancesWallets(TransactionDomain transactionDomain) {
        switch (transactionDomain.getTypeTransaction()) {
            case DEPOSIT -> transactionDomain.setCurrentBalanceDestinationWallet(
                    getDestinationBalanceSafely(transactionDomain)
            );
            case WITHDRAW -> transactionDomain.setCurrentBalanceSourceWallet(
                    getSourceBalanceSafely(transactionDomain)
            );
            case TRANSFER -> {
                transactionDomain.setCurrentBalanceSourceWallet(
                        getSourceBalanceSafely(transactionDomain)
                );
                transactionDomain.setCurrentBalanceDestinationWallet(
                        getDestinationBalanceSafely(transactionDomain)
                );
            }
            default -> throw new IllegalArgumentException(
                    "Unsupported transaction type: " + transactionDomain.getTypeTransaction()
            );
        }
    }

    private TransactionDomain createTransaction(TransactionDomain transactionDomain) {
        var completedEntity = transactionRepository.save(TransactionDomain.toEntityCompleted(transactionDomain));
        return TransactionDomain.fromEntity(completedEntity);
    }

    private void notifyAndPublishTransaction(TransactionDomain completedDomain) {
        notificationService.sendNotificationTransaction(completedDomain);
        producerEventsWalletTransaction.sendEventWalletTransaction(completedDomain);
    }

    private void syncingTransactionStatus(TransactionDomain started, TransactionDomain completed) {
        started.setStatusTransaction(completed.getStatusTransaction());
        started.setCodeTransaction(completed.getCodeTransaction());
    }

    private BigDecimal getSourceBalanceSafely(TransactionDomain transactionDomain) {
        try {
            return checkCurrentBalanceSourceRepository.checkBalanceWalletSource(transactionDomain.getSourceWallet());
        } catch (Exception e) {
            return checkBalanceWalletRepository.checkBalanceWallet(transactionDomain.getSourceWallet());
        }
    }

    private BigDecimal getDestinationBalanceSafely(TransactionDomain transactionDomain) {
        try {
            return checkCurrentBalanceDestinationRepository.checkBalanceWalletDestination(transactionDomain.getDestinationWallet());
        } catch (Exception e) {
            return checkBalanceWalletRepository.checkBalanceWallet(transactionDomain.getDestinationWallet());
        }
    }
}
