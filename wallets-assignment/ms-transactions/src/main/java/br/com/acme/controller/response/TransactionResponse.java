package br.com.acme.controller.response;

import br.com.acme.domain.StatusTransaction;
import br.com.acme.domain.TypeTransaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionResponse {
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
