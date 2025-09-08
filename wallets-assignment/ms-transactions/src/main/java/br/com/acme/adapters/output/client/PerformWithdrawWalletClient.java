package br.com.acme.adapters.output.client;

import br.com.acme.adapters.output.client.requests.WithdrawRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "performWithdrawWalletClient", url = "${client.url}")
public interface PerformWithdrawWalletClient {

    @PostMapping("/perform-withdraw")
    void performWithdraw(@RequestBody WithdrawRequest withdrawRequest);
}
