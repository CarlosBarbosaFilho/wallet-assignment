package br.com.acme.adapters.output.client.service;

import br.com.acme.adapters.output.client.PerformWithdrawWalletClient;
import br.com.acme.adapters.output.client.requests.WithdrawRequest;
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
    public Boolean performWithdraw(WithdrawRequest withdrawRequest) {
        try{
            this.performWithdrawWalletClient.performWithdraw(withdrawRequest);
            return true;
        }catch (FailedPerformedTransactionException ex) {
            createLogsCloudWatch.sendLog("This transaction with dates :: walletNumber"+withdrawRequest.getSourceWallet()+" " +
                    " and amount" +withdrawRequest.getAmount() + "don't  was processed your status is PENDING");
            return false;
        }
    }
}
