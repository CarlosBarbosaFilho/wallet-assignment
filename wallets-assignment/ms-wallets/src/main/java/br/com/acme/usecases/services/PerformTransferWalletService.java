package br.com.acme.usecases.services;

import br.com.acme.postgres.WalletRepositoryPostgres;
import br.com.acme.usecases.IPerformTransferWalletService;
import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformTransferWalletService implements IPerformTransferWalletService {

    private final WalletRepositoryPostgres walletRepositoryPostgres;

    @Override
    @Transactional
    public String performTransfer(String walletSource, String walletDestination, BigDecimal amount) {

        // Debit wallet source
        var walletSourceDebit = this.walletRepositoryPostgres.findWalletEntityByWalletNumber(walletSource);
        var newBalanceSource = walletSourceDebit.getBalance().subtract(amount);
        walletSourceDebit.setBalance(newBalanceSource);

        // Deposit wallet destination
        var walletDestinationCredit = this.walletRepositoryPostgres.findWalletEntityByWalletNumber(walletDestination);
        var newBalanceDestination = walletDestinationCredit.getBalance().add(amount);
        walletDestinationCredit.setBalance(newBalanceDestination);

        try {
            this.walletRepositoryPostgres.save(walletSourceDebit);
            this.walletRepositoryPostgres.save(walletDestinationCredit);
        }catch (OptimisticLockException e) {
            throw new RuntimeException("Concurrent update error, try again", e);
        }
        return "Transfer successfully";
    }
}
