package br.com.acme.usecases.services;

import br.com.acme.domain.model.TransactionDomain;
import br.com.acme.repository.TransactionRepository;
import br.com.acme.usecases.IGetTransactionByCodeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class GetTransactionByCodeService implements IGetTransactionByCodeService {

    private final TransactionRepository transactionRepository;

    @Override
    public TransactionDomain transactionByCode(UUID codeTransaction) {

        return TransactionDomain.fromEntity(this.transactionRepository.findTransactionByCodeTransaction(codeTransaction));
    }
}
