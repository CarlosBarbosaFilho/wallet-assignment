package br.com.acme.application.usecases;

import br.com.acme.application.domain.ClientDomain;
import br.com.acme.application.exceptions.ClientNotFoundException;
import br.com.acme.application.ports.in.IFindClientByDocumentUseCase;
import br.com.acme.application.ports.out.IFindClientByDocumentRepository;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class FindClientByDocumentUseCase implements IFindClientByDocumentUseCase {

    private final IFindClientByDocumentRepository findClientByDocumentRepository;

    @Override
    public ClientDomain find(String document) {
        var client = this.findClientByDocumentRepository.findByDocument(document).stream().findAny();
        if(client.isEmpty()) {
            throw new ClientNotFoundException("Client not found or does not exist");
        }
        return ClientDomain.createClientDomain(client.get());
    }
}
