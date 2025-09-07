package br.com.acme.application.ports.out;

import br.com.acme.domain.output.infra.entity.Client;

import java.util.List;

public interface IListClientsRepository {
    List<Client> list();
}
