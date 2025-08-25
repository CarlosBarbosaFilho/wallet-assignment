package br.com.acme.usecases.services;

import br.com.acme.postgres.WalletRepositoryPostgres;
import br.com.acme.usecases.IPerformWithdrawWalletService;
import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformWithdrawWalletService implements IPerformWithdrawWalletService {

    private final WalletRepositoryPostgres walletRepositoryPostgres;

    @Override
    @Transactional
    public String performWithdraw(String walletNumber, BigDecimal amount) {
        var wallet = this.walletRepositoryPostgres.findWalletEntityByWalletNumber(walletNumber);
        var newBalance = wallet.getBalance().subtract(amount);
        wallet.setBalance(newBalance);
        try {
            this.walletRepositoryPostgres.save(wallet);
        }catch (OptimisticLockException e) {
            throw new RuntimeException("Concurrent update error, try again", e);
        }
        return "Withdraw successfully";
    }
}
