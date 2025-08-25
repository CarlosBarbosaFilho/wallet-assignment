package br.com.acme.application.ports.out;

import br.com.acme.adapters.output.infra.entity.Client;

import java.util.List;

public interface IFindClientByDocumentRepository {
    List<Client> findByDocument(String document);
}
