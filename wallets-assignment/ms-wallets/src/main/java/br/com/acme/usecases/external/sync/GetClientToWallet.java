package br.com.acme.usecases.external.sync;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "callingExternalClientWallet", url = "${client.url}")
public interface GetClientToWallet {

    @GetMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    ClientResponse clientToWallet(@PathVariable("id") Long id);
}
