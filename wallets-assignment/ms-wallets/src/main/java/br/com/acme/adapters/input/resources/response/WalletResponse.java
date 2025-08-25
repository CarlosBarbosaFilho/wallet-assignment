package br.com.acme.adapters.input.resources.response;

import br.com.acme.domain.WalletStatus;
import br.com.acme.domain.WalletType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {

    private Long id;
    private String walletNumber;
    private WalletType walletType;
    private WalletStatus walletStatus;
    private BigDecimal balance;
    private String createdAt;
    private Long client;
}
