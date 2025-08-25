package br.com.acme.adapters.input.web;

import br.com.acme.adapters.input.resources.WalletsResource;
import br.com.acme.adapters.input.resources.request.WalletRequest;
import br.com.acme.adapters.input.resources.response.WalletResponse;
import br.com.acme.domain.WalletDomain;
import br.com.acme.domain.WalletStatus;
import br.com.acme.usecases.*;
import br.com.acme.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@AllArgsConstructor
public class WalletsController implements WalletsResource {

    private final Utils utils;
    private final ICreateWalletService createWalletService;
    private final ICheckBalanceWalletService checkBalanceWalletService;
    private final IGetWalletByClientDocumentService getWalletByClientDocumentService;
    private final IPerformDepositWalletService performDepositWalletService;
    private final IPerformWithdrawWalletService performWithdrawWalletService;
    private final IPerformTransferWalletService performTransferWalletService;

    @Override
    public WalletResponse create(WalletRequest request) {
        var response = this.createWalletService.createWallet(walletDomain(request));
        return this.walletResponse(response);
    }

    @Override
    public BigDecimal checkBalanceNumberWallet(String number) {
        return this.checkBalanceWalletService.getBalance(number);
    }

    @Override
    public BigDecimal checkBalanceDocumentClient(String document) {
        return this.getWalletByClientDocumentService.getWalletByClientDocument(document);
    }

    @Override
    public String performDeposit(String walletNumber, BigDecimal amount) {
        return performDepositWalletService.performDeposit(walletNumber, amount);
    }

    @Override
    public String performWithdraw(String walletNumber, BigDecimal amount) {
        return performWithdrawWalletService.performWithdraw(walletNumber, amount);
    }

    @Override
    public String performTransfer(String walletSource, String walletDestination, BigDecimal amount) {
        return performTransferWalletService.performTransfer(walletSource, walletDestination, amount);
    }

    private WalletDomain walletDomain(WalletRequest walletRequest) {
        return WalletDomain.builder()
                .walletNumber(walletRequest.getWalletNumber())
                .walletType(walletRequest.getWalletType())
                .client(walletRequest.getClient())
                .walletStatus(WalletStatus.ACTIVE)
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private WalletResponse walletResponse(WalletDomain walletDomain) {
        return WalletResponse.builder()
                .id(walletDomain.getId())
                .client(walletDomain.getClient())
                .walletNumber(walletDomain.getWalletNumber())
                .walletStatus(walletDomain.getWalletStatus())
                .walletType(walletDomain.getWalletType())
                .balance(walletDomain.getBalance())
                .createdAt(utils.formatDate(walletDomain.getCreatedAt()))
                .build();
    }
}
