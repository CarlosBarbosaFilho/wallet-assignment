package br.com.acme.adapters.output.infra.entity;

import br.com.acme.application.domain.StatusClient;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class Client {

    private Long id;
    private String name;
    private String email;
    private String document;
    private BigDecimal income;
    private LocalDateTime createdAt;
    private StatusClient status;

    @DynamoDbPartitionKey
    public Long getId() {
        return id;
    }

    @DynamoDbAttribute("name")
    public String getName() {
        return name;
    }

    @DynamoDbAttribute("email")
    @DynamoDbSecondaryPartitionKey(indexNames = {"email-index"})
    public String getEmail() {
        return email;
    }

    @DynamoDbAttribute("document")
    @DynamoDbSecondaryPartitionKey(indexNames = {"document-index"})
    public String getDocument() {
        return document;
    }

    @DynamoDbAttribute("income")
    public BigDecimal getIncome() {
        return income;
    }

    @DynamoDbAttribute("createdAt")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @DynamoDbAttribute("isActive")
    public StatusClient getStatus() {
        return status;
    }
}
