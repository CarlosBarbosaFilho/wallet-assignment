package br.com.acme.application.usecases;

import br.com.acme.application.domain.ClientDomain;
import br.com.acme.application.exceptions.ClientNotFoundException;
import br.com.acme.application.ports.in.IFindClientByEmailUseCase;
import br.com.acme.application.ports.out.IFindClientByEmailRepository;
import br.com.acme.utils.UseCase;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class FindClientByEmailUseCase implements IFindClientByEmailUseCase {

    private final IFindClientByEmailRepository findClientByEmailRepository;

    @Override
    public ClientDomain find(String email) {
        var client = this.findClientByEmailRepository.findByEmail(email).stream().findFirst();
        if(client.isEmpty()) {
            throw new ClientNotFoundException("Client not found or does not exist");
        }
        return ClientDomain.createClientDomain(client.get());
    }
}
