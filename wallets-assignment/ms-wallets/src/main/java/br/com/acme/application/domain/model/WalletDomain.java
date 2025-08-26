package br.com.acme.application.domain.model;

import br.com.acme.adapters.ouput.database.entity.WalletEntity;
import br.com.acme.application.domain.WalletStatus;
import br.com.acme.application.domain.WalletType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalletDomain {

    private Long id;
    private String walletNumber;
    private WalletType walletType;
    private WalletStatus walletStatus;
    private BigDecimal balance;
    private Long client;
    private String clientDocument;
    private LocalDateTime createdAt;

    public static WalletEntity createWalletEntity(WalletDomain walletDomain) {
        return WalletEntity.builder()
                .walletNumber(walletDomain.walletNumber)
                .walletType(walletDomain.walletType)
                .walletStatus(walletDomain.walletStatus)
                .balance(walletDomain.balance)
                .client(walletDomain.client)
                .clientDocument(walletDomain.clientDocument)
                .createdAt(LocalDateTime.now().withNano(0))
                .build();
    }

    public static WalletDomain createWalletDomain(WalletEntity wallet){
        return WalletDomain.builder()
                .id(wallet.getId())
                .walletType(wallet.getWalletType())
                .walletNumber(wallet.getWalletNumber())
                .balance(wallet.getBalance())
                .clientDocument(wallet.getClientDocument())
                .client(wallet.getClient())
                .createdAt(wallet.getCreatedAt())
                .walletStatus(wallet.getWalletStatus())
                .build();
    }
}
