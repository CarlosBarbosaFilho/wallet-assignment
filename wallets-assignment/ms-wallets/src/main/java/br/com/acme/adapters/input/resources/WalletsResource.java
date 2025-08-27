package br.com.acme.adapters.input.resources;

import br.com.acme.adapters.input.resources.request.WalletRequest;
import br.com.acme.adapters.input.resources.response.WalletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequestMapping("/api/v1/wallets")
public interface WalletsResource {

    @PostMapping
    WalletResponse create(@Valid @RequestBody WalletRequest request);

    @GetMapping("/check-balance")
    BigDecimal checkBalanceNumberWallet(@RequestParam("walletNumber") String walletNumber);

    @GetMapping("/client-document")
    BigDecimal checkBalanceDocumentClient(@RequestParam("document") String document);

    @GetMapping("/perform-deposit")
    String performDeposit(@RequestParam("walletNumber") String walletNumber, @RequestParam("amount") BigDecimal amount);

    @GetMapping("/perform-withdraw")
    String performWithdraw(@RequestParam("walletNumber") String walletNumber, @RequestParam("amount") BigDecimal amount);

    @GetMapping("/perform-transfer")
    String performTransfer(@RequestParam("walletSource") String walletSource,
                           @RequestParam("walletDestination") String walletDestination,
                           @RequestParam("amount") BigDecimal amount);

    @GetMapping("/wallet-number")
    ResponseEntity<WalletResponse> findWalletByWalletNumber(@RequestParam("walletNumber") String walletNumber);
}
