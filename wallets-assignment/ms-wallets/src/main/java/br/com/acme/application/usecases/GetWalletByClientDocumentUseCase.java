package br.com.acme.application.usecases;

import br.com.acme.application.ports.in.IGetWalletByClientDocumentUseCase;
import br.com.acme.application.ports.out.CheckWalletDocumentClientRepository;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@UseCase
@AllArgsConstructor
public class GetWalletByClientDocumentUseCase implements IGetWalletByClientDocumentUseCase {

    private final CheckWalletDocumentClientRepository checkWalletDocumentClientRepository;

    @Override
    public BigDecimal getBalanceWalletByClientDocument(String document) {
        return checkWalletDocumentClientRepository.getBalanceWalletByDocumentClient(document).getBalance();
    }
}
