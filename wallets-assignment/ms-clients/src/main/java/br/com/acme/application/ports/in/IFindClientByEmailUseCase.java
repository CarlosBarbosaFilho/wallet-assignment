package br.com.acme.application.ports.in;

import br.com.acme.application.domain.ClientDomain;

public interface IFindClientByEmailUseCase {

    ClientDomain find(String email);
}
