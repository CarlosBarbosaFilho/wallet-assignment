package br.com.acme.application.usecases;

import br.com.acme.adapters.output.infra.repository.ClientRepository;
import br.com.acme.application.domain.ClientDomain;
import br.com.acme.application.ports.in.IListAllClientsUseCase;
import br.com.acme.application.ports.out.IListClientsRepository;
import br.com.acme.application.utils.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@UseCase
@AllArgsConstructor
public class ListAllClientsUseCase implements IListAllClientsUseCase {

    private final IListClientsRepository listClientsRepository;

    @Override
    public List<ClientDomain> listAll() {
        var response = listClientsRepository.list();
        return  response.stream().map(ClientDomain::createClientDomain).toList();
    }
}
