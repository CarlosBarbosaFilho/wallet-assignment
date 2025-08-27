package br.com.acme.adapters.output.client.response;

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
