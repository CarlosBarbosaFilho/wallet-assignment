package br.com.acme.application.ports.out;

import br.com.acme.adapters.ouput.database.entity.WalletEntity;

public interface CheckWalletDocumentClientRepository {
    WalletEntity getBalanceWalletByDocumentClient(String document);
}
