package br.com.acme.adapters.output.client.service;

import br.com.acme.adapters.output.client.PerformTransferWalletClient;
import br.com.acme.adapters.output.client.requests.TransferRequest;
import br.com.acme.application.exceptions.FailedPerformedTransactionException;
import br.com.acme.application.ports.out.CreateLogsCloudWatch;
import br.com.acme.application.ports.out.IPerformTransferTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformTransferTransactionClient implements IPerformTransferTransactionRepository {

    private final CreateLogsCloudWatch createLogsCloudWatch;
    private final PerformTransferWalletClient performTransferWalletClient;

    @Override
    public Boolean performTransfer (TransferRequest transferRequest) {
        try{
            this.performTransferWalletClient.performTransfer(transferRequest);
            return true;
        }catch (FailedPerformedTransactionException ex) {
            createLogsCloudWatch.sendLog("This transaction with dates of "+transferRequest.getSourceWallet()+ " and " +
                 transferRequest.getDestinationWallet()+   " and amount" +transferRequest.getAmount() + "don't  was processed your status is PENDING");
            return false;
        }
    }
}
