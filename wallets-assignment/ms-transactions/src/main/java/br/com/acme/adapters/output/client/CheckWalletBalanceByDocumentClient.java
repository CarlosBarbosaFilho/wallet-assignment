package br.com.acme.adapters.output.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "checkWalletBalanceClientByDocument", url = "${client.url}")
public interface CheckWalletBalanceByDocumentClient {

    @GetMapping(value = "/check-balance", consumes = "application/json", produces = "application/json")
    BigDecimal checkWalletBalance(@RequestParam("walletNumber") String walletNumber);
}
