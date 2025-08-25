package br.com.acme.adapters.output.database.service;

import br.com.acme.adapters.output.client.PerformWithdrawWalletClient;
import br.com.acme.application.ports.out.IPerformWithdrawTransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PerformWithdrawTransactionRepository implements IPerformWithdrawTransactionRepository {

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
