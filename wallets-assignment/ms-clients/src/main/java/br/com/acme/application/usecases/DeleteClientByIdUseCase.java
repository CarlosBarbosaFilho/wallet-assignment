package br.com.acme.application.usecases;

import br.com.acme.application.exceptions.BusinessException;
import br.com.acme.application.ports.in.IDeleteClientByIdUseCase;
import br.com.acme.application.ports.out.IDeleteClientByIdRepository;
import br.com.acme.application.utils.UseCase;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class DeleteClientByIdUseCase implements IDeleteClientByIdUseCase {

    private final IDeleteClientByIdRepository deleteClient;

    @Override
    public String delete(Long id) {
        try {
            deleteClient.deleteClient(id);
        } catch (BusinessException e) {
            throw new BusinessException("Client was don't deleted");
        }
        return "Client was deleted";
    }
}
