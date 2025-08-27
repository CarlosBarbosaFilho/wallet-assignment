package br.com.acme.adapters.output.client.service;

import br.com.acme.adapters.output.client.CheckWalletBalanceClient;
import br.com.acme.application.exceptions.FailedPerformedTransactionException;
import br.com.acme.application.ports.out.CreateLogsCloudWatch;
import br.com.acme.application.ports.out.ICheckBalanceWalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CheckBalanceWalletClient implements ICheckBalanceWalletRepository {

    private final CreateLogsCloudWatch createLogsCloudWatch;
    private CheckWalletBalanceClient checkWalletBalanceClient;

    @Override
    public BigDecimal checkBalanceWallet(String walletNumber) {
        try {
            return checkWalletBalanceClient.checkWalletBalance(walletNumber);
        } catch (FailedPerformedTransactionException ex) {
            createLogsCloudWatch.sendLog("The balance to walletNumber" + walletNumber + " not found");
            throw new FailedPerformedTransactionException("Failed to check wallet balance for wallet: " + walletNumber);
        }
    }
}
