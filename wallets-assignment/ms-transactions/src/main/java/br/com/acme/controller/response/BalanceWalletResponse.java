package br.com.acme.controller.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class BalanceWalletResponse {
    private String message;
}
