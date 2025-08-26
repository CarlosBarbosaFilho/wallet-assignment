package br.com.acme.application.usecases;

import br.com.acme.application.ports.in.IPerformWithdrawWalletUseCase;
import br.com.acme.application.ports.out.CreateWalletRepository;
import br.com.acme.application.ports.out.GetWalletToTransactionRepository;
import br.com.acme.utils.UseCase;
import jakarta.persistence.OptimisticLockException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@UseCase
@AllArgsConstructor
public class PerformWithdrawWalletUseCase implements IPerformWithdrawWalletUseCase {

    private final GetWalletToTransactionRepository getWalletToTransactionRepository;
    private final CreateWalletRepository createWalletRepository;

    @Override
    @Transactional
    public String performWithdraw(String walletNumber, BigDecimal amount) {
        var wallet = this.getWalletToTransactionRepository.walletToTransaction(walletNumber);
        var newBalance = wallet.getBalance().subtract(amount);
        wallet.setBalance(newBalance);
        try {
            this.createWalletRepository.createWallet(wallet);
        }catch (OptimisticLockException e) {
            throw new RuntimeException("Concurrent update error, try again", e);
        }
        return "Withdraw successfully";
    }
}
