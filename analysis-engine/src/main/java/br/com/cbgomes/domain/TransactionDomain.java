package br.com.cbgomes.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDomain implements Serializable {

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
