package br.com.acme.adapters.output.client;

import br.com.acme.adapters.output.client.requests.DepositRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "MS-WALLET", contextId = "performDepositWalletClient")
public interface PerformDepositWalletClient {
    @PostMapping("/api/v1/wallets/perform-deposit")
    void performDeposit(@RequestBody DepositRequest depositRequest);
}
