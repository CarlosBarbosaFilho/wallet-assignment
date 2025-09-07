package br.com.acme.infrastructure.external.consumer.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
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
}
