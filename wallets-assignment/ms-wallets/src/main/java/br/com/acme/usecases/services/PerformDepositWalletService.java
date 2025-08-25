package br.com.acme.usecases.services;

import br.com.acme.postgres.WalletRepositoryPostgres;
import br.com.acme.usecases.IPerformDepositWalletService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformDepositWalletService implements IPerformDepositWalletService {

    private final WalletRepositoryPostgres walletRepositoryPostgres;

    @Override
    @Transactional
    public String performDeposit(String walletNumber, BigDecimal amount) {
        var wallet = this.walletRepositoryPostgres.findWalletEntityByWalletNumber(walletNumber);
        if (wallet == null ) {
            return "Wallet don't exists, please confirm destination wallet to deposit";
        }
        var newBalance = wallet.getBalance().add(amount);
        wallet.setBalance(newBalance);
        this.walletRepositoryPostgres.save(wallet);
        return "Deposit successfully";
    }
}
