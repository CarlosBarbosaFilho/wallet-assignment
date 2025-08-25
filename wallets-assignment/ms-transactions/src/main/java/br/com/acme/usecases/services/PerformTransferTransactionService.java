package br.com.acme.usecases.services;

import br.com.acme.usecases.IPerformTransferTransactionService;
import br.com.acme.usecases.external.sync.PerformTransferWalletClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformTransferTransactionService implements IPerformTransferTransactionService {

    private final PerformTransferWalletClient performTransferWalletClient;

    @Override
    public Boolean performTransfer (String walletSource, String walletDestination, BigDecimal amount) {
        try{
            this.performTransferWalletClient.performTransfer(walletSource, walletDestination, amount);
            return true;
        }catch (NullPointerException ex) {
            ex.getMessage();
            return false;
        }
    }
}
