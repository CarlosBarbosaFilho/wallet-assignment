package br.com.acme.application.usecases;

import br.com.acme.application.domain.model.WalletDomain;
import br.com.acme.application.ports.in.IGetWalletByNumber;
import br.com.acme.utils.UseCase;

@UseCase
public class GetWalletByWalletNumber implements IGetWalletByNumber {



    @Override
    public WalletDomain findWallet(String walletNumber) {
        return null;
    }
}
