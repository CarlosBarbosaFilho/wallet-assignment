package br.com.acme.application.usecases.transactions;

import br.com.acme.application.domain.StatusTransaction;
import br.com.acme.application.domain.model.TransactionDomain;
import br.com.acme.application.ports.in.IPerformDepositUseCase;
import br.com.acme.application.ports.out.IPerformDepositTransactionRepository;
import br.com.acme.application.ports.out.IProducerEventsWalletTransaction;
import br.com.acme.application.usecases.share.CompleteTransactionProcess;
import br.com.acme.application.usecases.share.FallbackTransactionProcess;
import br.com.acme.application.usecases.share.UpdateWalletBalanceProcess;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;

import java.util.UUID;


@UseCase
@AllArgsConstructor
public class PerformDepositUseCase implements IPerformDepositUseCase {

    private final IPerformDepositTransactionRepository performDepositTransactionService;
    private final IProducerEventsWalletTransaction producerEventsWalletTransaction;
    private final CompleteTransactionProcess completeTransaction;
    private final FallbackTransactionProcess fallbackTransactionProcess;
    private final UpdateWalletBalanceProcess updateWalletBalanceProcess;

    @Override
    public TransactionDomain deposit(TransactionDomain transactionDomain) {
        updateWalletBalanceProcess.updateWalletBalance(transactionDomain);

        if (this.performDepositTransactionService
                .performDeposit(transactionDomain.getDestinationWallet(), transactionDomain.getAmountTransaction())) {
            this.completeTransaction.completeTransaction(transactionDomain);
        }else {
            transactionDomain.setStatusTransaction(StatusTransaction.FAILED);
            this.producerEventsWalletTransaction.sendEventWalletTransaction(transactionDomain);
            transactionDomain.setCodeTransaction(UUID.randomUUID());
            transactionDomain.setStatusTransaction(StatusTransaction.PENDING);
            fallbackTransactionProcess.fallbackCreateTransactionOnRedis(transactionDomain);
        }

        return transactionDomain;
    }
}
