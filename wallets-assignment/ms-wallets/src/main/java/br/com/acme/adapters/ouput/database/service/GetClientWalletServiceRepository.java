package br.com.acme.adapters.ouput.database.service;

import br.com.acme.adapters.ouput.client.ClientResponse;
import br.com.acme.adapters.ouput.client.GetClientToWallet;
import br.com.acme.application.ports.out.GetClientWalletClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetClientWalletServiceRepository implements GetClientWalletClient {

    private final GetClientToWallet clientToWallet;

    @Override
    public ClientResponse clientToWallet(Long id) {
        return this.clientToWallet.clientToWallet(id);
    }
}
