package br.com.acme.adapters.output.kafka.producer;

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
public class WalletsTransactionEvent {

    private Long id;
    private String sourceWallet;
    private String emailSourceWallet;
    private String destinationWallet;
    private BigDecimal amountTransaction;
    private TypeTransaction typeTransaction;
    private StatusTransaction statusTransaction;
    private UUID codeTransaction;
    private LocalDateTime createdAt;
}
