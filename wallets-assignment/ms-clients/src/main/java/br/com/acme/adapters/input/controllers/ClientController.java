package br.com.acme.adapters.input.controllers;

import br.com.acme.application.mapper.ConverterMapper;
import br.com.acme.application.ports.in.*;
import br.com.acme.adapters.input.api.ClientsResources;
import br.com.acme.adapters.input.requests.ClientRequest;
import br.com.acme.adapters.input.response.ClientResponse;
import br.com.acme.application.domain.ClientDomain;
import br.com.acme.application.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ClientController implements ClientsResources {

    private final Utils utils;

    private final ICreateClientUseCase createClientService;
    private final IListAllClientsUseCase listAllClientsService;
    private final IFindClientByIdUseCase findClientByIdService;
    private final IDeleteClientByIdUseCase deleteClientByIdService;
    private final IFindClientByEmailUseCase findClientByEmailService;
    private final IFindClientByDocumentUseCase findClientByDocumentService;

    private final ConverterMapper converterMapper;

    @Override
    public ClientResponse create(ClientRequest request) {

        return createResponse(this.createClientService.execute(createDomain(request)));
    }

    @Override
    public List<ClientResponse> list() {
        return listAllClientsService.listAll().stream().map(this::createResponse).toList();
    }

    @Override
    public ClientResponse getClient(Long id) {
        return createResponse(this.findClientByIdService.find(id));
    }

    @Override
    public ClientResponse getClientEmail(String email) {
        return createResponse(this.findClientByEmailService.find(email));
    }

    @Override
    public ClientResponse getClientDocument(String document) {
        return createResponse(this.findClientByDocumentService.find(document));
    }

    @Override
    public String deleteClient(Long id) {
        return deleteClientByIdService.delete(id);
    }

    private ClientDomain createDomain(ClientRequest request) {
       return (ClientDomain) this.converterMapper.convertObject(request, ClientDomain.class);
    }

    private ClientResponse createResponse(ClientDomain domain) {
        var response = (ClientResponse) this.converterMapper.convertObject(domain, ClientResponse.class);
        response.setCreatedAt(utils.formatDate(domain.getCreatedAt()));
        return response;
    }
}
