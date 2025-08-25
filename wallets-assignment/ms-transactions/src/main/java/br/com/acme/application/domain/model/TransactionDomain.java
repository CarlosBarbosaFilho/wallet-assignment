package br.com.acme.application.domain.model;

import br.com.acme.adapters.output.database.entity.TransactionEntity;
import br.com.acme.application.domain.BalanceStatus;
import br.com.acme.application.domain.StatusTransaction;
import br.com.acme.application.domain.TypeTransaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDomain {

    private Long id;
    private String sourceWallet;
    private String emailSourceWallet;
    private String destinationWallet;
    private BigDecimal amountTransaction;
    private TypeTransaction typeTransaction;
    private StatusTransaction statusTransaction;
    private UUID codeTransaction;
    private LocalDateTime createdAt;
    private BigDecimal currentBalanceSourceWallet;
    private BigDecimal currentBalanceDestinationWallet;

    public BalanceStatus hasNoBalance(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) <= 0) {
            return BalanceStatus.NO_BALANCE;
        }

        if (balance.compareTo(this.amountTransaction) < 0) {
            return BalanceStatus.INSUFFICIENT_BALANCE;
        }

        return BalanceStatus.SUFFICIENT_BALANCE;
    }

    public static TransactionDomain fromEntity(TransactionEntity entity) {
        return TransactionDomain.builder()
                .id(entity.getId())
                .amountTransaction(entity.getAmountTransaction())
                .sourceWallet(entity.getSourceWallet())
                .destinationWallet(entity.getDestinationWallet())
                .typeTransaction(entity.getTypeTransaction())
                .codeTransaction(entity.getCodeTransaction())
                .statusTransaction(entity.getStatusTransaction())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static TransactionEntity toEntityPending(TransactionDomain transactionDomain) {
        return TransactionEntity.builder()
                .currentBalanceDestinationWallet(transactionDomain.currentBalanceDestinationWallet)
                .currentBalanceSourceWallet(transactionDomain.currentBalanceSourceWallet)
                .sourceWallet(transactionDomain.sourceWallet)
                .destinationWallet(transactionDomain.destinationWallet)
                .amountTransaction(transactionDomain.amountTransaction)
                .typeTransaction(transactionDomain.typeTransaction)
                .createdAt(LocalDateTime.now().withNano(0))
                .statusTransaction(StatusTransaction.PENDING)
                .codeTransaction(UUID.randomUUID())
                .build();
    }

    public static TransactionEntity toEntityCompleted(TransactionDomain transactionDomain) {
        return TransactionEntity.builder()
                .sourceWallet(transactionDomain.sourceWallet)
                .destinationWallet(transactionDomain.destinationWallet)
                .amountTransaction(transactionDomain.amountTransaction)
                .typeTransaction(transactionDomain.typeTransaction)
                .statusTransaction(StatusTransaction.COMPLETED)
                .codeTransaction(transactionDomain.codeTransaction)
                .createdAt(transactionDomain.createdAt)
                .currentBalanceDestinationWallet(transactionDomain.currentBalanceDestinationWallet)
                .currentBalanceSourceWallet(transactionDomain.currentBalanceSourceWallet)
                .build();
    }

    public static TransactionEntity toEntityFailed(TransactionDomain transactionDomain) {
        return TransactionEntity.builder()
                .currentBalanceDestinationWallet(transactionDomain.currentBalanceDestinationWallet)
                .currentBalanceSourceWallet(transactionDomain.currentBalanceSourceWallet)
                .sourceWallet(transactionDomain.sourceWallet)
                .destinationWallet(transactionDomain.destinationWallet)
                .amountTransaction(transactionDomain.amountTransaction)
                .typeTransaction(transactionDomain.typeTransaction)
                .statusTransaction(StatusTransaction.FAILED)
                .codeTransaction(transactionDomain.codeTransaction)
                .createdAt(transactionDomain.createdAt)
                .build();
    }
}
