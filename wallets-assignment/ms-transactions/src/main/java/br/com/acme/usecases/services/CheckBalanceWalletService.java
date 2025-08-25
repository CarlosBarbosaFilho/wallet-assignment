package br.com.acme.usecases.services;

import br.com.acme.usecases.ICheckBalanceWalletService;
import br.com.acme.usecases.external.sync.CheckWalletBalanceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CheckBalanceWalletService implements ICheckBalanceWalletService {

    private CheckWalletBalanceClient checkWalletBalanceClient;

    @Override
    public BigDecimal checkBalanceWallet(String walletNumber) {
        return checkWalletBalanceClient.checkWalletBalance(walletNumber);
    }
}
