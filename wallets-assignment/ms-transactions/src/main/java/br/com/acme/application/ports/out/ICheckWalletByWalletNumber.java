package br.com.acme.application.ports.out;

import br.com.acme.adapters.output.client.response.WalletResponse;

public interface ICheckWalletByWalletNumber {
    WalletResponse getWalletByNumber(String walletNumber);
}
