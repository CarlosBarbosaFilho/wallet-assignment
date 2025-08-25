package br.com.acme.controller.request;

import br.com.acme.domain.TypeTransaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @NotBlank(message = "Field sourceWallet is required")
    private String sourceWallet;

    @NotBlank(message = "Field destinationWallet is required")
    private String destinationWallet;

    @NotNull(message = "Field amountTransaction is required")
    @Positive(message = "Transaction amount must be greater than zero")
    private BigDecimal amountTransaction;

    @NotNull(message = "Field typeTransaction is required")
    private TypeTransaction typeTransaction;
}
