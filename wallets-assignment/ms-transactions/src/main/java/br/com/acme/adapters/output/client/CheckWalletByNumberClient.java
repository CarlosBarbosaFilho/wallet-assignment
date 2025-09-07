package br.com.acme.adapters.output.client;

import br.com.acme.adapters.output.client.response.WalletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "checkWalletByWalletNumber", url = "${client.url}")
public interface CheckWalletByNumberClient {

    @GetMapping(value = "/wallet-number", consumes = "application/json", produces = "application/json")
    WalletResponse checkWalletByNumber(@RequestParam("walletNumber") String walletNumber);
}
