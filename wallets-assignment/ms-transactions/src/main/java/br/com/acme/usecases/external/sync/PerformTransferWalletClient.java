package br.com.acme.usecases.external.sync;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "PerformTransferWalletClient", url = "${client.url}")
public interface PerformTransferWalletClient {

    @GetMapping("/perform-transfer")
    void performTransfer(@RequestParam("walletSource") String walletSource,
                           @RequestParam("walletDestination") String walletDestination,
                           @RequestParam("amount") BigDecimal amount);
}
