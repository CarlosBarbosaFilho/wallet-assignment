package br.com.acme.application.ports.in;

import br.com.acme.application.domain.ClientDomain;

import java.util.List;

public interface IListAllClientsUseCase {

    List<ClientDomain> listAll();
}
