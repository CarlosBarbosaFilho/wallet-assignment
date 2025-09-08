package br.com.acme.adapters.input.resources;

import br.com.acme.adapters.input.resources.request.DepositRequest;
import br.com.acme.adapters.input.resources.request.TransferRequest;
import br.com.acme.adapters.input.resources.request.WalletRequest;
import br.com.acme.adapters.input.resources.request.WithdrawRequest;
import br.com.acme.adapters.input.resources.response.WalletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequestMapping("/api/v1/wallets")
public interface WalletsResource {


    @GetMapping("/check-balance")
    BigDecimal checkBalanceNumberWallet(@RequestParam("walletNumber") String walletNumber);

    @GetMapping("/client-document")
    BigDecimal checkBalanceDocumentClient(@RequestParam("document") String document);

    @GetMapping("/wallet-number")
    ResponseEntity<WalletResponse> findWalletByWalletNumber(@RequestParam("walletNumber") String walletNumber);

    @PostMapping
    WalletResponse create(@Valid @RequestBody WalletRequest request);

    @PostMapping("/perform-deposit")
    String deposit(@Valid @RequestBody DepositRequest depositRequest);

    @PostMapping("/perform-withdraw")
    String withdraw(@Valid @RequestBody WithdrawRequest withdrawRequest);

    @PostMapping("/perform-transfer")
    String transfer(@Valid @RequestBody TransferRequest transferRequest);


}
