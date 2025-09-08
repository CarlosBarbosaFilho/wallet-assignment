package br.com.acme.kafka;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class WalletsTransactionEvent {

    private Long id;
    private String sourceWallet;
    private String emailSourceWallet;
    private String destinationWallet;
    private BigDecimal amountTransaction;
    private String typeTransaction;
    private String statusTransaction;
    private UUID codeTransaction;
    private LocalDateTime createdAt;
    private BigDecimal currentBalanceSourceWallet;
    private BigDecimal currentBalanceDestinationWallet;
}
