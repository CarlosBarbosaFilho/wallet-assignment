package br.com.acme.adapters.ouput.database.service;

import br.com.acme.adapters.ouput.database.entity.WalletEntity;
import br.com.acme.adapters.ouput.database.repository.WalletRepository;
import br.com.acme.application.ports.out.CreateWalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateWalletServiceRepository implements CreateWalletRepository {

    private final WalletRepository walletRepository;

    @Override
    public WalletEntity createWallet(WalletEntity entity) {
        return this.walletRepository.save(entity);
    }
}
