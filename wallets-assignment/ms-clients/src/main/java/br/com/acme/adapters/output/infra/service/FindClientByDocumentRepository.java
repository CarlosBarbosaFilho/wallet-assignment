package br.com.acme.adapters.output.infra.service;

import br.com.acme.adapters.output.infra.entity.Client;
import br.com.acme.application.domain.StatusClient;
import br.com.acme.application.ports.out.IFindClientByDocumentRepository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;

@Service
public class FindClientByDocumentRepository implements IFindClientByDocumentRepository {

    private final DynamoDbTable<Client> clientTable;

    public FindClientByDocumentRepository(DynamoDbEnhancedClient enhancedClient) {
        this.clientTable = enhancedClient.table("client", TableSchema.fromBean(Client.class));
    }

    @Override
    public List<Client> findByDocument(String document) {
        QueryConditional condition = QueryConditional.keyEqualTo(
                Key.builder().partitionValue(document).build()
        );

        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(condition)
                .build();

        return clientTable.index("document-index")
                .query(request)
                .stream()
                .flatMap(page -> page.items().stream())
                .filter(c -> !c.getStatus().equals(StatusClient.INACTIVE))
                .toList();
    }
}
