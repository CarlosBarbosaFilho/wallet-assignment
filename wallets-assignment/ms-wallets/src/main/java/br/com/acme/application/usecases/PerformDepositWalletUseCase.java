package br.com.acme.application.usecases;

import br.com.acme.adapters.ouput.database.repository.WalletRepository;
import br.com.acme.application.ports.in.IPerformDepositWalletUseCase;
import br.com.acme.application.ports.out.GetWalletToTransactionRepository;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@UseCase
@AllArgsConstructor
public class PerformDepositWalletUseCase implements IPerformDepositWalletUseCase {

    private final WalletRepository walletRepository;
    private final GetWalletToTransactionRepository walletToTransactionRepository;


    @Override
    @Transactional
    public String performDeposit(String walletNumber, BigDecimal amount) {
        var wallet = this.walletToTransactionRepository.walletToTransaction(walletNumber);
        if (wallet == null ) {
            return "Wallet don't exists, please confirm destination wallet to deposit";
        }
        var newBalance = wallet.getBalance().add(amount);
        wallet.setBalance(newBalance);
        this.walletRepository.save(wallet);
        return "Deposit successfully";
    }
}
