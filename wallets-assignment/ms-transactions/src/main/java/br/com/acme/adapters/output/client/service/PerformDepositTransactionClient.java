package br.com.acme.adapters.output.client.service;

import br.com.acme.adapters.output.client.PerformDepositWalletClient;
import br.com.acme.adapters.output.client.requests.DepositRequest;
import br.com.acme.application.exceptions.FailedPerformedTransactionException;
import br.com.acme.application.ports.out.CreateLogsCloudWatch;
import br.com.acme.application.ports.out.IPerformDepositTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformDepositTransactionClient implements IPerformDepositTransactionRepository {

    private final CreateLogsCloudWatch createLogsCloudWatch;
    private final PerformDepositWalletClient performDepositWalletClient;

    @Override

    public Boolean performDeposit(DepositRequest depositRequest) {
        try{
            this.performDepositWalletClient.performDeposit(depositRequest);
            return true;
        }catch (FailedPerformedTransactionException msg) {
            createLogsCloudWatch.sendLog("This transaction with dates :: walletNumber"+depositRequest.getDestinationWallet()+" " +
                    " and amount" +depositRequest.getAmount() + "don't  was processed your status is PENDING");
            return false;
        }
    }
}
