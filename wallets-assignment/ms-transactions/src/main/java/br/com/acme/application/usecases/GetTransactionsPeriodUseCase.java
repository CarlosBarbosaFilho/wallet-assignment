package br.com.acme.application.usecases;

import br.com.acme.adapters.output.database.repository.TransactionRepository;
import br.com.acme.application.domain.model.TransactionDomain;
import br.com.acme.application.mapper.ConverterMapper;
import br.com.acme.application.ports.in.IGetTransactionsByPeriodUseCase;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@UseCase
@AllArgsConstructor
public class GetTransactionsPeriodUseCase implements IGetTransactionsByPeriodUseCase {

    private final TransactionRepository transactionRepository;
    private final ConverterMapper converterMapper;

    @Override
    public List<TransactionDomain> getTransactionsPeriod(String walletNumber, LocalDateTime startDate, LocalDateTime endDate) {
        var response = this.transactionRepository.findBySourceWalletAndCreatedAtBetween(walletNumber, startDate, endDate);
        return (List<TransactionDomain>)  converterMapper.convertLIstObjects(response, TransactionDomain.class);
    }
}
