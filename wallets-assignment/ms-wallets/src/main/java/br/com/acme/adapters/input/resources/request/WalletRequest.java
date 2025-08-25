package br.com.acme.adapters.input.resources.request;

import br.com.acme.domain.WalletType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletRequest {

    @NotNull(message = "walletNumber is required")
    private String walletNumber;
    @NotNull(message = "walletType is required")
    private WalletType walletType;
    @NotNull(message = "client is required")
    private Long client;
}
