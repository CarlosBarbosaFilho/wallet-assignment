package br.com.acme.adapters.output.database.service;

import br.com.acme.adapters.output.client.PerformTransferWalletClient;
import br.com.acme.application.exceptions.FailedPerformedTransactionException;
import br.com.acme.application.ports.out.CreateLogsCloudWatch;
import br.com.acme.application.ports.out.IPerformTransferTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformTransferTransactionRepository implements IPerformTransferTransactionRepository {

    private final CreateLogsCloudWatch createLogsCloudWatch;
    private final PerformTransferWalletClient performTransferWalletClient;

    @Override
    public Boolean performTransfer (String walletSource, String walletDestination, BigDecimal amount) {
        try{
            this.performTransferWalletClient.performTransfer(walletSource, walletDestination, amount);
            return true;
        }catch (FailedPerformedTransactionException ex) {
            createLogsCloudWatch.sendLog("This transaction with dates of "+walletSource+ " and " +
                 walletDestination+   " and amount" +amount + "don't  was processed your status is PENDING");
            return false;
        }
    }
}
