package br.com.acme.domain.model;

import br.com.acme.domain.WalletStatus;
import br.com.acme.domain.WalletType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_wallets")
public class WalletEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_number")
    private String walletNumber;

    @Column(name = "wallet_type")
    @Enumerated(EnumType.STRING)
    private WalletType walletType;

    @Column(name = "wallet_status")
    @Enumerated(EnumType.STRING)
    private WalletStatus walletStatus;

    @Column(name = "wallet_balance")
    private BigDecimal balance;

    @Column(name = "client_id")
    private Long client;

    @Column(name = "client_document")
    private String clientDocument;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "version")
    @Version
    private Integer version;

}
