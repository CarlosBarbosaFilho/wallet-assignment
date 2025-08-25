package br.com.acme.usecases.services;

import br.com.acme.usecases.IPerformWithdrawTransactionService;
import br.com.acme.usecases.external.sync.PerformWithdrawWalletClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformWithdrawTransactionService implements IPerformWithdrawTransactionService {

    private final PerformWithdrawWalletClient performWithdrawWalletClient;

    @Override
    public Boolean performWithdraw(String walletNumber, BigDecimal amount) {
        try{
            this.performWithdrawWalletClient.performWithdraw(walletNumber, amount);
            return true;
        }catch (NullPointerException ex) {
            ex.getMessage();
            return false;
        }
    }
}
