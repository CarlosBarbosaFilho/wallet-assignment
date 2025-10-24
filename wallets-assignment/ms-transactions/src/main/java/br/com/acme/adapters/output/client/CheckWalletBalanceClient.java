package br.com.acme.adapters.output.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "MS-WALLET", contextId = "checkWalletBalanceClient")
public interface CheckWalletBalanceClient {

    @GetMapping(value = "/api/v1/wallets/check-balance", consumes = "application/json", produces = "application/json")
    BigDecimal checkWalletBalance(@RequestParam("walletNumber") String walletNumber);
}
