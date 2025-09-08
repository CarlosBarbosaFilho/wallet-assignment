package br.com.acme.application.usecases.transactions;

import br.com.acme.adapters.output.client.requests.TransferRequest;
import br.com.acme.application.domain.StatusTransaction;
import br.com.acme.application.domain.model.TransactionDomain;
import br.com.acme.application.exceptions.WalletDestinationEqualsWalletSource;
import br.com.acme.application.ports.in.IPerformTransferUseCase;
import br.com.acme.application.ports.out.IPerformTransferTransactionRepository;
import br.com.acme.application.ports.out.IProducerEventsWalletTransaction;
import br.com.acme.application.usecases.share.CompleteTransactionProcess;
import br.com.acme.application.usecases.share.FallbackTransactionProcess;
import br.com.acme.application.usecases.share.UpdateWalletBalanceProcess;
import br.com.acme.application.usecases.share.ValidWalletBalanceProcess;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@AllArgsConstructor
public class PerformTransferUseCase implements IPerformTransferUseCase {

    private final IPerformTransferTransactionRepository performTransferTransactionRepository;
    private final IProducerEventsWalletTransaction producerEventsWalletTransaction;
    private final CompleteTransactionProcess completeTransaction;
    private final FallbackTransactionProcess fallbackTransactionProcess;
    private final UpdateWalletBalanceProcess updateWalletBalanceProcess;
    private final ValidWalletBalanceProcess validateBalance;

    @Override
    @Transactional
    public TransactionDomain transfer(TransactionDomain transactionDomain) {
        validateBalance.validateBalance(transactionDomain);
        updateWalletBalanceProcess.updateWalletBalance(transactionDomain);

        if (validWalletsInTransaction(transactionDomain)){
            throw new WalletDestinationEqualsWalletSource("The source wallet is the same as the destination wallet");
        }

        if (this.performTransferTransactionRepository.performTransfer(
                TransferRequest.builder()
                        .destinationWallet(transactionDomain.getDestinationWallet())
                        .sourceWallet(transactionDomain.getSourceWallet())
                        .amount(transactionDomain.getAmountTransaction())
                        .build()
        )) {
            completeTransaction.completeTransaction(transactionDomain);

        }else {
            transactionDomain.setStatusTransaction(StatusTransaction.FAILED);
            this.producerEventsWalletTransaction.sendEventWalletTransaction(transactionDomain);
            transactionDomain.setCodeTransaction(UUID.randomUUID());
            fallbackTransactionProcess.fallbackCreateTransactionOnRedis(transactionDomain);
        }

        return transactionDomain;
    }

    private Boolean validWalletsInTransaction(TransactionDomain transactionDomain) {
        return transactionDomain.getSourceWallet().equalsIgnoreCase(transactionDomain.getDestinationWallet());
    }
}
