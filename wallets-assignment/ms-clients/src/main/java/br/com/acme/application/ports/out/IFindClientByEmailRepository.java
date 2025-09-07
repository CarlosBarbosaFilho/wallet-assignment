package br.com.acme.application.ports.out;

import br.com.acme.domain.output.infra.entity.Client;

import java.util.List;

public interface IFindClientByEmailRepository {
    List<Client> findByEmail(String email);
}
