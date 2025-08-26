package br.com.acme.application.usecases;

import br.com.acme.application.ports.in.ICheckBalanceWalletUseCase;
import br.com.acme.application.exception.BusinessException;
import br.com.acme.application.ports.out.CheckWalletBalanceRepository;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@UseCase
@AllArgsConstructor
public class CheckBalanceWalletUseCase implements ICheckBalanceWalletUseCase {

    private final CheckWalletBalanceRepository checkWalletBalanceRepository;

    @Override
    public BigDecimal getBalance(String walletNumber) {
        var balance = this.checkWalletBalanceRepository.checkBalance(walletNumber);
        if (balance == null ) {
            throw new BusinessException("Wallet not found or not exists");
        }
        return balance.getBalance();
    }
}
