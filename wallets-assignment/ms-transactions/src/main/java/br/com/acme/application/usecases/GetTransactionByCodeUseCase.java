package br.com.acme.application.usecases;

import br.com.acme.adapters.output.database.repository.TransactionRepository;
import br.com.acme.application.domain.model.TransactionDomain;
import br.com.acme.application.ports.in.IGetTransactionByCodeUseCase;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@UseCase
@AllArgsConstructor
public class GetTransactionByCodeUseCase implements IGetTransactionByCodeUseCase {

    private final TransactionRepository transactionRepository;

    @Override
    public TransactionDomain transactionByCode(UUID codeTransaction) {

        return TransactionDomain.fromEntity(this.transactionRepository.findTransactionByCodeTransaction(codeTransaction));
    }
}
