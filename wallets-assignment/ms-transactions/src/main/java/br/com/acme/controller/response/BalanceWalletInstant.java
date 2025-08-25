package br.com.acme.controller.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class BalanceWalletInstant {
    private String balance;
}
