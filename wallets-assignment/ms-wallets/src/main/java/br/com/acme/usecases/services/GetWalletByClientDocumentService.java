package br.com.acme.usecases.services;

import br.com.acme.domain.WalletDomain;
import br.com.acme.postgres.WalletRepositoryPostgres;
import br.com.acme.usecases.IGetWalletByClientDocumentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static br.com.acme.domain.WalletDomain.createWalletDomain;

@Service
@AllArgsConstructor
public class GetWalletByClientDocumentService implements IGetWalletByClientDocumentService {

    private final WalletRepositoryPostgres walletRepositoryPostgres;

    @Override
    public BigDecimal getWalletByClientDocument(String document) {
        return walletRepositoryPostgres.findWalletEntityByClientDocument(document).getBalance();
    }
}
