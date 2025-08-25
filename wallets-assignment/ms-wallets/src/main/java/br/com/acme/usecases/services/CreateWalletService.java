package br.com.acme.usecases.services;

import br.com.acme.domain.WalletDomain;
import br.com.acme.postgres.WalletRepositoryPostgres;
import br.com.acme.usecases.ICreateWalletService;
import br.com.acme.usecases.external.sync.GetClientToWallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.acme.domain.WalletDomain.createWalletDomain;
import static br.com.acme.domain.WalletDomain.createWalletEntity;

@Service
@AllArgsConstructor
public class CreateWalletService implements ICreateWalletService {

    private final WalletRepositoryPostgres walletRepositoryPostgres;
    private final GetClientToWallet clientToWallet;

    @Override
    public WalletDomain createWallet(WalletDomain walletDomain) {
        var client = clientToWallet.clientToWallet(walletDomain.getClient());
        walletDomain.setClient(client.getId());
        walletDomain.setClientDocument(client.getDocument());
        var entity = createWalletEntity(walletDomain);
        return createWalletDomain(walletRepositoryPostgres.save(entity));
    }
}
