package br.com.acme.adapters.output.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "performDepositWalletClient", url = "${client.url}")
public interface PerformDepositWalletClient {
    @GetMapping("/perform-deposit")
    void performDeposit(@RequestParam("walletNumber") String walletNumber, @RequestParam("amount") BigDecimal amount);
}
