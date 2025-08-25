package br.com.acme.adapters.output.database.service;

import br.com.acme.adapters.output.client.CheckWalletBalanceClient;
import br.com.acme.application.ports.out.ICheckBalanceWalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CheckBalanceWalletRepository implements ICheckBalanceWalletRepository {

    private CheckWalletBalanceClient checkWalletBalanceClient;

    @Override
    public BigDecimal checkBalanceWallet(String walletNumber) {
        return checkWalletBalanceClient.checkWalletBalance(walletNumber);
    }
}
