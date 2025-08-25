package br.com.acme.usecases.services;

import br.com.acme.usecases.IPerformDepositTransactionService;
import br.com.acme.usecases.external.sync.PerformDepositWalletClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformDepositTransactionService implements IPerformDepositTransactionService {

    private final PerformDepositWalletClient performDepositWalletClient;

    @Override
    public Boolean performDeposit(String walletNumber, BigDecimal amount) {
        try{
            this.performDepositWalletClient.performDeposit(walletNumber, amount);
            return true;
        }catch (NullPointerException ex) {
            ex.getMessage();
            return false;
        }
    }
}
