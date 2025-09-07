package br.com.acme.domain.output.infra.repository;

import br.com.acme.domain.output.infra.entity.Client;

import java.util.List;

public interface ClientRepository {

    Client save(Client client);
    List<Client> list();
    Client getClient(Long id);
    void deleteClient(Long id);
    List<Client> findByEmail(String email);
    List<Client> findByDocument(String document);
}
