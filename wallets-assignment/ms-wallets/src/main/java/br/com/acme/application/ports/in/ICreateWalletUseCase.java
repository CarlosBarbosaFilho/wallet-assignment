package br.com.acme.application.ports.in;

import br.com.acme.application.domain.model.WalletDomain;

public interface ICreateWalletUseCase {
    WalletDomain createWallet(WalletDomain walletDomain);
}
