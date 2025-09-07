package br.com.acme.domain.output.infra.service;

import br.com.acme.domain.output.infra.entity.Client;
import br.com.acme.application.ports.out.IFindClientByIdRepository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Service
public class FindClientByIdRepository implements IFindClientByIdRepository {

    private final DynamoDbTable<Client> clientTable;

    public FindClientByIdRepository(DynamoDbEnhancedClient enhancedClient) {
        this.clientTable = enhancedClient.table("client", TableSchema.fromBean(Client.class));
    }

    @Override
    public Client getClient(Long id) {
        return clientTable.getItem(Key.builder().partitionValue(id).build());
    }
}
