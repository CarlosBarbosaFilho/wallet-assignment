package br.com.acme.adapters.input.web.request;

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
public class TransactionWithdraw {

    @NotBlank(message = "Field sourceWallet is required")
    private String sourceWallet;

    @NotNull(message = "Field amountTransaction is required")
    @Positive(message = "Transaction amount must be greater than zero")
    private BigDecimal amountTransaction;
}
