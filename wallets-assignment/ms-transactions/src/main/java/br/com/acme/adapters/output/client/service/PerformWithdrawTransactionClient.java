package br.com.acme.adapters.output.client.service;

import br.com.acme.adapters.output.client.PerformWithdrawWalletClient;
import br.com.acme.application.exceptions.FailedPerformedTransactionException;
import br.com.acme.application.ports.out.CreateLogsCloudWatch;
import br.com.acme.application.ports.out.IPerformWithdrawTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformWithdrawTransactionClient implements IPerformWithdrawTransactionRepository {

    private final CreateLogsCloudWatch createLogsCloudWatch;
    private final PerformWithdrawWalletClient performWithdrawWalletClient;

    @Override
    public Boolean performWithdraw(String walletNumber, BigDecimal amount) {
        try{
            this.performWithdrawWalletClient.performWithdraw(walletNumber, amount);
            return true;
        }catch (FailedPerformedTransactionException ex) {
            createLogsCloudWatch.sendLog("This transaction with dates :: walletNumber"+walletNumber+" " +
                    " and amount" +amount + "don't  was processed your status is PENDING");
            return false;
        }
    }
}
