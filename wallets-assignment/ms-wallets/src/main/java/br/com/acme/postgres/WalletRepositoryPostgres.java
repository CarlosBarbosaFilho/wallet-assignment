package br.com.acme.postgres;


import br.com.acme.domain.model.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface WalletRepositoryPostgres extends JpaRepository<WalletEntity, Long> {

    WalletEntity findWalletEntityByWalletNumber(String walletNumber);
    WalletEntity findWalletEntityByClientDocument(String document);
}
