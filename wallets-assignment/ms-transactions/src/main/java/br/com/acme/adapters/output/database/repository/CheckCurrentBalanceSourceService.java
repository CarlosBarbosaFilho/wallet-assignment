package br.com.acme.adapters.output.database.repository;


import br.com.acme.application.ports.out.ICheckCurrentBalanceSourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CheckCurrentBalanceSourceService implements ICheckCurrentBalanceSourceRepository {

    private final TransactionRepository transactionRepository;

    @Override
    public BigDecimal checkBalanceWalletSource(String walletNumber) {
        return this.transactionRepository.findTransactionBySourceWallet(walletNumber).getCurrentBalanceSourceWallet();
    }
}
