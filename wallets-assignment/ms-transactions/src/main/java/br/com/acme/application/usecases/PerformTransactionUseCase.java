package br.com.acme.application.usecases;

import br.com.acme.adapters.output.kafka.producer.WalletsTransactionEvent;
import br.com.acme.adapters.output.sns.NotificationTransactionsClient;
import br.com.acme.adapters.output.database.repository.TransactionRepository;
import br.com.acme.application.domain.StatusTransaction;
import br.com.acme.application.domain.BalanceStatus;
import br.com.acme.application.domain.model.TransactionDomain;
import br.com.acme.application.exceptions.InsufficientBalanceException;
import br.com.acme.application.exceptions.WalletDestinationEqualsWalletSource;
import br.com.acme.application.ports.out.*;
import br.com.acme.application.ports.in.IPerformTransactionUseCase;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.time.LocalDateTime;

@UseCase
@AllArgsConstructor
public class PerformTransactionUseCase implements IPerformTransactionUseCase {



    private final TransactionRepository transactionRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private final IProducerEventsWalletTransaction producerEventsWalletTransaction;

    private final NotificationTransactionsClient notificationService;
    private final ICheckBalanceWalletRepository checkBalanceWalletRepository;

    private final IPerformTransferTransactionRepository performTransferTransactionService;
    private final IPerformWithdrawTransactionRepository performWithdrawTransactionService;
    private final IPerformDepositTransactionRepository performDepositTransactionService;

    @Override
    public TransactionDomain createTransaction(TransactionDomain transactionDomain) {
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

        var result = performDepositTransactionService.performDeposit(
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

        if (validWalletsInTransaction(transactionDomain)){
            throw new WalletDestinationEqualsWalletSource("The source wallet is the same as the destination wallet");
        }

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
        var balance = checkBalanceWalletRepository.checkBalanceWallet(transactionDomain.getSourceWallet());
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
        var actualBalanceWalletSource = this.checkBalanceWalletRepository.checkBalanceWallet(transactionDomain.getSourceWallet());
        var actualBalanceWalletDestination = this.checkBalanceWalletRepository.checkBalanceWallet(transactionDomain.getDestinationWallet());

        transactionDomain.setCurrentBalanceSourceWallet(actualBalanceWalletSource);
        transactionDomain.setCurrentBalanceDestinationWallet(actualBalanceWalletDestination);

        var completedEntity = transactionRepository.save(TransactionDomain.toEntityCompleted(transactionDomain));
        var completedDomain = TransactionDomain.fromEntity(completedEntity);
        notificationService.sendNotificationTransaction(completedDomain);
        this.producerEventsWalletTransaction.sendEventWalletTransaction(completedDomain);

        transactionDomain.setStatusTransaction(completedDomain.getStatusTransaction());
        transactionDomain.setCodeTransaction(completedDomain.getCodeTransaction());

    }

    private void starterTransaction(TransactionDomain transactionDomain) {
        var actualBalanceWalletSource = this.checkBalanceWalletRepository.checkBalanceWallet(transactionDomain.getSourceWallet());
        var actualBalanceWalletDestination = this.checkBalanceWalletRepository.checkBalanceWallet(transactionDomain.getDestinationWallet());
        transactionDomain.setCurrentBalanceSourceWallet(actualBalanceWalletSource);
        transactionDomain.setCurrentBalanceDestinationWallet(actualBalanceWalletDestination);
    }

    private Boolean validWalletsInTransaction(TransactionDomain transactionDomain) {
        return transactionDomain.getSourceWallet().equalsIgnoreCase(transactionDomain.getDestinationWallet());
    }
}
