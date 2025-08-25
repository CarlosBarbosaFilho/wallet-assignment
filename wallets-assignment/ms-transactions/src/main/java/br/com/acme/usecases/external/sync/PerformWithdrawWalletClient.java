package br.com.acme.usecases.external.sync;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "performWithdrawWalletClient", url = "${client.url}")
public interface PerformWithdrawWalletClient {

    @GetMapping("/perform-withdraw")
    void performWithdraw(@RequestParam("walletNumber") String walletNumber, @RequestParam("amount") BigDecimal amount);
}
