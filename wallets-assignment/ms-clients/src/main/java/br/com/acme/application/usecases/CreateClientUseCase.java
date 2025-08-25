package br.com.acme.application.usecases;

import br.com.acme.application.domain.ClientDomain;
import br.com.acme.application.exceptions.ClientAlreadyExistsException;
import br.com.acme.application.ports.in.ICreateClientUseCase;
import br.com.acme.application.ports.out.ICreateClientRepository;
import br.com.acme.application.ports.out.IFindClientByDocumentRepository;
import br.com.acme.application.ports.out.IFindClientByEmailRepository;
import br.com.acme.application.utils.UseCase;
import lombok.AllArgsConstructor;

import static br.com.acme.application.domain.ClientDomain.createClient;
import static br.com.acme.application.domain.ClientDomain.createClientDomain;

@UseCase
@AllArgsConstructor
public class CreateClientUseCase implements ICreateClientUseCase {

    private final ICreateClientRepository createClientRepository;
    private final IFindClientByEmailRepository findClientByEmailRepository;
    private final IFindClientByDocumentRepository findClientByDocumentRepository;

    @Override
    public ClientDomain execute(ClientDomain clientDomain) {
        validateUniqueClient(clientDomain);
        var createEntity = createClient(clientDomain);
        var client = createClientRepository.save(createEntity);
        return createClientDomain(client);
    }

    private void validateUniqueClient(ClientDomain clientDomain) {
        if (!findClientByEmailRepository.findByEmail(clientDomain.getEmail()).isEmpty()) {
            throw new ClientAlreadyExistsException("Client with this email already exists, please provide another one.");
        }

        if (!findClientByDocumentRepository.findByDocument(clientDomain.getDocument()).isEmpty()) {
            throw new ClientAlreadyExistsException("Client with this document already exists, please provide another one.");
        }
    }
}
