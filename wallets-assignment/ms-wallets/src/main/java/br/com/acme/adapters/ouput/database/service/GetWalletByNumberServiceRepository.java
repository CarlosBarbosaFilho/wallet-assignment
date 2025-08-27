package br.com.acme.adapters.ouput.database.service;

import br.com.acme.adapters.ouput.database.entity.WalletEntity;
import br.com.acme.adapters.ouput.database.repository.WalletRepository;
import br.com.acme.application.ports.out.GetWalletByWalletNumberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetWalletByNumberServiceRepository implements GetWalletByWalletNumberRepository {

    private final WalletRepository walletRepository;

    @Override
    public WalletEntity getWalletByNumber(String walletNumber) {
        return this.walletRepository.findWalletEntityByWalletNumber(walletNumber);
    }
}
