package br.com.acme.application.ports.in;

import br.com.acme.application.domain.model.WalletDomain;

public interface IGetWalletByNumber {

    WalletDomain findWallet(String walletNumber);
}
