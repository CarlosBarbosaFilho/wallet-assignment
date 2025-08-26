package br.com.acme.application.usecases;

import br.com.acme.application.domain.model.WalletDomain;
import br.com.acme.application.ports.in.ICreateWalletUseCase;
import br.com.acme.application.ports.out.CreateWalletRepository;
import br.com.acme.application.ports.out.GetClientWalletClient;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;

import static br.com.acme.application.domain.model.WalletDomain.createWalletDomain;
import static br.com.acme.application.domain.model.WalletDomain.createWalletEntity;

@UseCase
@AllArgsConstructor
public class CreateWalletUseCaseUseCase implements ICreateWalletUseCase {

    private final CreateWalletRepository createWalletRepository;
    private final GetClientWalletClient clientWalletClient;

    @Override
    public WalletDomain createWallet(WalletDomain walletDomain) {
        var client = clientWalletClient.clientToWallet(walletDomain.getClient());
        walletDomain.setClient(client.getId());
        walletDomain.setClientDocument(client.getDocument());
        var entity = createWalletEntity(walletDomain);
        return createWalletDomain(createWalletRepository.createWallet(entity));
    }
}
