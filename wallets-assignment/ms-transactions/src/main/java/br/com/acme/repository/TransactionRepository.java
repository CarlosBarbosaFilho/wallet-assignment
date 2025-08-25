package br.com.acme.repository;

import br.com.acme.domain.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository <TransactionEntity, Long> {

    TransactionEntity findTransactionByCodeTransaction(UUID codeTransaction);

    @Query("SELECT t FROM TransactionEntity t " +
            "WHERE (t.sourceWallet = :walletNumber OR t.destinationWallet = :walletNumber) " +
            "AND t.createdAt <= :date " +
            "AND t.statusTransaction = 'COMPLETED' " +
            "ORDER BY t.createdAt DESC")
    List<TransactionEntity> findLastCompletedTransactionUntilDate(@Param("walletNumber") String walletNumber,
                                                                  @Param("date") LocalDateTime date);

    List<TransactionEntity> findBySourceWalletAndCreatedAtBetween(
            String walletNumber,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
