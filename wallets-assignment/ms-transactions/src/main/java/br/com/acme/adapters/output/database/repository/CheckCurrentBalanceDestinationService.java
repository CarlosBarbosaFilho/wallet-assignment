package br.com.acme.adapters.output.database.repository;

import br.com.acme.application.ports.out.ICheckCurrentBalanceDestinationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CheckCurrentBalanceDestinationService  implements ICheckCurrentBalanceDestinationRepository {

    private final TransactionRepository transactionRepository;


    @Override
    public BigDecimal checkBalanceWalletDestination(String walletNumber) {
        return this.transactionRepository.findTransactionByDestinationWallet(walletNumber).getCurrentBalanceDestinationWallet();
    }
}
