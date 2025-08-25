package br.com.acme.application.ports.out;

import br.com.acme.adapters.output.infra.entity.Client;

public interface IFindClientByIdRepository {
    Client getClient(Long id);
}
