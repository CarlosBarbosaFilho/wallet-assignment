package br.com.acme.adapters.output.client.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepositRequest {

    @NotBlank(message = "Field destinationWallet is required")
    private String destinationWallet;

    @NotNull(message = "Field amountTransaction is required")
    @Positive(message = "Transaction amount must be greater than zero")
    private BigDecimal amount;
}
