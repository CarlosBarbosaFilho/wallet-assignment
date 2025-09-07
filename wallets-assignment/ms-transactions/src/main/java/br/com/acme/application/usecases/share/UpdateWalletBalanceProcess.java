package br.com.acme.application.usecases.share;

import br.com.acme.application.domain.TypeTransaction;
import br.com.acme.application.domain.model.TransactionDomain;
import br.com.acme.application.ports.out.ICheckBalanceWalletRepository;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class UpdateWalletBalanceProcess {

    private final ICheckBalanceWalletRepository checkBalanceWalletRepository;

    public void updateWalletBalance(TransactionDomain transactionDomain) {
        TypeTransaction type = transactionDomain.getTypeTransaction();

        switch (type) {
            case DEPOSIT -> updateDestinationBalance(transactionDomain);
            case WITHDRAW -> updateSourceBalance(transactionDomain);
            case TRANSFER -> {
                updateSourceBalance(transactionDomain);
                updateDestinationBalance(transactionDomain);
            }
            default -> throw new IllegalArgumentException("Unsupported transaction type: " + type);
        }
    }

    private void updateSourceBalance(TransactionDomain transactionDomain) {
        var actualBalance = checkBalanceWalletRepository.checkBalanceWallet(transactionDomain.getSourceWallet());
        transactionDomain.setCurrentBalanceSourceWallet(actualBalance);
    }

    private void updateDestinationBalance(TransactionDomain transactionDomain) {
        var actualBalance = checkBalanceWalletRepository.checkBalanceWallet(transactionDomain.getDestinationWallet());
        transactionDomain.setCurrentBalanceDestinationWallet(actualBalance);
    }
}
