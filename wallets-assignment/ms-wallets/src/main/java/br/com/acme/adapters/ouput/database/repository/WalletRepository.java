package br.com.acme.adapters.ouput.database.repository;


import br.com.acme.adapters.ouput.database.entity.WalletEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

    WalletEntity findWalletEntityByWalletNumber(String walletNumber);

    WalletEntity findWalletEntityByClientDocument(String document);
}
