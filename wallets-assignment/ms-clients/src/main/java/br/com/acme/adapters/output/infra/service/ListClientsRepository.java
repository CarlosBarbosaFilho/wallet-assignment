package br.com.acme.adapters.output.infra.service;

import br.com.acme.adapters.output.infra.entity.Client;
import br.com.acme.application.domain.StatusClient;
import br.com.acme.application.ports.out.IListClientsRepository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListClientsRepository implements IListClientsRepository {

    private final DynamoDbTable<Client> clientTable;

    public ListClientsRepository(DynamoDbEnhancedClient enhancedClient) {
        this.clientTable = enhancedClient.table("client", TableSchema.fromBean(Client.class));
    }

    @Override
    public List<Client> list() {
        return clientTable.scan().items().stream()
                .filter(c -> !c.getStatus().equals(StatusClient.INACTIVE))
                .collect(Collectors.toList());
    }
}
