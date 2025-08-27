package br.com.acme.adapters.output.database.service;

import br.com.acme.adapters.output.client.PerformDepositWalletClient;
import br.com.acme.application.exceptions.FailedPerformedTransactionException;
import br.com.acme.application.ports.out.CreateLogsCloudWatch;
import br.com.acme.application.ports.out.IPerformDepositTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformDepositTransactionRepository implements IPerformDepositTransactionRepository {

    private final CreateLogsCloudWatch createLogsCloudWatch;
    private final PerformDepositWalletClient performDepositWalletClient;

    @Override

    public Boolean performDeposit(String walletNumber, BigDecimal amount) {
        try{
            this.performDepositWalletClient.performDeposit(walletNumber, amount);
            return true;
        }catch (FailedPerformedTransactionException msg) {
            createLogsCloudWatch.sendLog("This transaction with dates :: walletNumber"+walletNumber+" " +
                    " and amount" +amount + "don't  was processed your status is PENDING");
            return false;
        }
    }
}
