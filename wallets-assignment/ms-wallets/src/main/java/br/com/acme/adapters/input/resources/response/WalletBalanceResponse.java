package br.com.acme.adapters.input.resources.response;


import br.com.acme.application.domain.WalletType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletBalanceResponse {

    private String walletNumber;
    private WalletType walletType;
    private BigDecimal balance;
}
