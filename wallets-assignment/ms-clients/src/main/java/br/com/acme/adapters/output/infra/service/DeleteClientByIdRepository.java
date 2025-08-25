package br.com.acme.adapters.output.infra.service;

import br.com.acme.adapters.output.infra.entity.Client;
import br.com.acme.application.domain.StatusClient;
import br.com.acme.application.ports.out.IDeleteClientByIdRepository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Objects;

@Service
public class DeleteClientByIdRepository implements IDeleteClientByIdRepository {

    private final DynamoDbTable<Client> clientTable;

    public DeleteClientByIdRepository(DynamoDbEnhancedClient enhancedClient) {
        this.clientTable = enhancedClient.table("client", TableSchema.fromBean(Client.class));
    }

    @Override
    public void deleteClient(Long id) {
        Client client = clientTable.getItem(Key.builder().partitionValue(id).build());
        Objects.requireNonNull(client).setStatus(StatusClient.INACTIVE);
        clientTable.putItem(client);
    }
}
