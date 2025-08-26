package br.com.acme.application.usecases;

import br.com.acme.adapters.ouput.database.repository.WalletRepository;
import br.com.acme.application.ports.in.IPerformTransferWalletUseCase;
import br.com.acme.application.ports.out.GetWalletToTransactionRepository;
import br.com.acme.utils.UseCase;
import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@UseCase
@AllArgsConstructor
public class PerformTransferWalletUseCase implements IPerformTransferWalletUseCase {

    private final WalletRepository walletRepository;
    private final GetWalletToTransactionRepository walletToTransactionRepository;

    @Override
    @Transactional
    public String performTransfer(String walletSource, String walletDestination, BigDecimal amount) {

        // Debit wallet source
        var walletSourceDebit = this.walletToTransactionRepository.walletToTransaction(walletSource);
        var newBalanceSource = walletSourceDebit.getBalance().subtract(amount);
        walletSourceDebit.setBalance(newBalanceSource);

        // Deposit wallet destination
        var walletDestinationCredit = this.walletToTransactionRepository.walletToTransaction(walletDestination);
        var newBalanceDestination = walletDestinationCredit.getBalance().add(amount);
        walletDestinationCredit.setBalance(newBalanceDestination);

        try {
            this.walletRepository.save(walletSourceDebit);
            this.walletRepository.save(walletDestinationCredit);
        }catch (OptimisticLockException e) {
            throw new RuntimeException("Concurrent update error, try again", e);
        }
        return "Transfer successfully";
    }
}
