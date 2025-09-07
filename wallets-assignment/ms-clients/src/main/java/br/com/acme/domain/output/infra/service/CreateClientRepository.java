package br.com.acme.domain.output.infra.service;

import br.com.acme.domain.output.infra.entity.Client;
import br.com.acme.application.domain.StatusClient;
import br.com.acme.application.ports.out.ICreateClientRepository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.time.LocalDateTime;

@Service
public class CreateClientRepository implements ICreateClientRepository {

    private final DynamoDbTable<Client> clientTable;

    public CreateClientRepository(DynamoDbEnhancedClient enhancedClient) {
        this.clientTable = enhancedClient.table("client", TableSchema.fromBean(Client.class));
    }

    public Client save(Client client) {
        client.setId(System.currentTimeMillis());
        client.setCreatedAt(LocalDateTime.now());
        client.setStatus(StatusClient.ACTIVE);
        clientTable.putItem(client);
        return client;
    }
}
