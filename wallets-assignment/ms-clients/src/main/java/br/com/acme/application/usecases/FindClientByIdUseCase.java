package br.com.acme.application.usecases;

import br.com.acme.application.domain.ClientDomain;
import br.com.acme.application.exceptions.ClientNotFoundException;
import br.com.acme.application.ports.in.IFindClientByIdUseCase;
import br.com.acme.application.ports.out.IFindClientByIdRepository;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;

import static br.com.acme.application.domain.ClientDomain.createClientDomain;

@UseCase
@AllArgsConstructor
public class FindClientByIdUseCase implements IFindClientByIdUseCase {

    private final IFindClientByIdRepository findClientByIdRepository;

    @Override
    public ClientDomain find(Long id) {
            var client =  findClientByIdRepository.getClient(id);
            if (client == null) {
                throw new ClientNotFoundException("Client not found or does not exist");
            }
            return createClientDomain(client);
    }
}
