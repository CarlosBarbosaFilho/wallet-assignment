package br.com.acme.usecases.services;

import br.com.acme.domain.model.TransactionDomain;
import br.com.acme.mapper.ConverterMapper;
import br.com.acme.repository.TransactionRepository;
import br.com.acme.usecases.IGetTransactionsByPeriod;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GetTransactionsPeriod implements IGetTransactionsByPeriod {

    private final TransactionRepository transactionRepository;
    private final ConverterMapper converterMapper;

    @Override
    public List<TransactionDomain> getTransactionsPeriod(String walletNumber, LocalDateTime startDate, LocalDateTime endDate) {
        var response = this.transactionRepository.findBySourceWalletAndCreatedAtBetween(walletNumber, startDate, endDate);
        return (List<TransactionDomain>)  converterMapper.convertLIstObjects(response, TransactionDomain.class);
    }
}
