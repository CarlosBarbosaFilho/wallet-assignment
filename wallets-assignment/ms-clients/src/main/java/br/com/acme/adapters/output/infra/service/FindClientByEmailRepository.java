package br.com.acme.adapters.output.infra.service;

import br.com.acme.adapters.output.infra.entity.Client;
import br.com.acme.application.domain.StatusClient;
import br.com.acme.application.ports.out.IFindClientByEmailRepository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;

@Service
public class FindClientByEmailRepository implements IFindClientByEmailRepository {

    private final DynamoDbTable<Client> clientTable;

    public FindClientByEmailRepository(DynamoDbEnhancedClient enhancedClient) {
        this.clientTable = enhancedClient.table("client", TableSchema.fromBean(Client.class));
    }

    @Override
    public List<Client> findByEmail(String email) {
        QueryConditional condition = QueryConditional.keyEqualTo(
                Key.builder().partitionValue(email).build()
        );

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(condition)
                .build();

        return clientTable.index("email-index")
                .query(request)
                .stream()
                .flatMap(page -> page.items().stream())
                .filter(c -> !c.getStatus().equals(StatusClient.INACTIVE))
                .toList();
    }
}
