package br.com.acme.usecases.services;

import br.com.acme.postgres.WalletRepositoryPostgres;
import br.com.acme.usecases.ICheckBalanceWalletService;
import br.com.acme.usecases.exception.BusinessException;
import br.com.acme.usecases.exception.WalletNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CheckBalanceWalletService implements ICheckBalanceWalletService {

    private final WalletRepositoryPostgres walletRepositoryPostgres;

    @Override
    public BigDecimal getBalance(String walletNumber) {
        var balance = this.walletRepositoryPostgres.findWalletEntityByWalletNumber(walletNumber);
        if (balance == null ) {
            throw new BusinessException("Wallet not found or not exists");
        }
        return balance.getBalance();
    }
}
