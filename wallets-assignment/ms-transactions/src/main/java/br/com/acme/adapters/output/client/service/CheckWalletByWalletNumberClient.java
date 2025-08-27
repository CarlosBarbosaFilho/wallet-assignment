package br.com.acme.adapters.output.client.service;

import br.com.acme.adapters.output.client.response.WalletResponse;
import br.com.acme.application.ports.out.ICheckWalletByWalletNumber;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CheckWalletByWalletNumberClient implements ICheckWalletByWalletNumber {

    private final CheckWalletByWalletNumberClient checkWalletByWalletNumberClient;

    @Override
    public WalletResponse getWalletByNumber(String walletNumber) {
        return this.checkWalletByWalletNumberClient.getWalletByNumber(walletNumber);
    }
}
