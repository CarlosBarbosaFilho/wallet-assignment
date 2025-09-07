package br.com.acme.application.usecases.share;

import br.com.acme.application.domain.BalanceStatus;
import br.com.acme.application.domain.model.TransactionDomain;
import br.com.acme.application.exceptions.InsufficientBalanceException;
import br.com.acme.application.ports.out.ICheckBalanceWalletRepository;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class ValidWalletBalanceProcess {

    private final ICheckBalanceWalletRepository checkBalanceWalletRepository;

    public void validateBalance(TransactionDomain transactionDomain) {
        var actualBalanceWalletSource = this.checkBalanceWalletRepository.checkBalanceWallet(transactionDomain.getSourceWallet());

        if (transactionDomain.hasNoBalance(actualBalanceWalletSource)
                .equals(BalanceStatus.NO_BALANCE)) {
            throw new InsufficientBalanceException("Insufficient balance to make the transfer");

        }else if (transactionDomain.hasNoBalance(actualBalanceWalletSource)
                .equals(BalanceStatus.INSUFFICIENT_BALANCE)){
            throw new InsufficientBalanceException("There is a balance but it is not enough to make the transfer");
        }
    }
}
