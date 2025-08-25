package br.com.acme.application.ports.out;

import br.com.acme.adapters.output.infra.entity.Client;

import java.util.List;

public interface IFindClientByEmailRepository {
    List<Client> findByEmail(String email);
}
