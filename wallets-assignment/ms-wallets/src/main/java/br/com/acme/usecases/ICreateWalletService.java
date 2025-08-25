package br.com.acme.usecases;

import br.com.acme.domain.WalletDomain;

public interface ICreateWalletService {

    WalletDomain createWallet(WalletDomain walletDomain);
}
