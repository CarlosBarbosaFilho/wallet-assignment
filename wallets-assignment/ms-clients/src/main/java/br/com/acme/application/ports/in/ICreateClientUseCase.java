package br.com.acme.application.ports.in;

import br.com.acme.application.domain.ClientDomain;

public interface ICreateClientUseCase {

    ClientDomain execute(ClientDomain clientDomain);
}
