package br.com.acme.adapters.input.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BalanceWalletResponse {
    private String message;
}
