package br.com.acme.application.ports.out;

import br.com.acme.adapters.ouput.client.ClientResponse;

public interface GetClientWalletClient {
    ClientResponse clientToWallet(Long id);
}
