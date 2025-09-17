package br.com.acme.infrastructure.external.consumer;

import br.com.acme.config.aws.ses.NotificationClient;
import br.com.acme.infrastructure.external.consumer.response.TransactionDomain;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
public class ListenTransactionService implements ListenTransactionQueueSuccess {

    @Autowired
    private ObjectMapper objectMapper;
    private final NotificationClient notificationClient;

    @SqsListener("transactions-wallets")
    public TransactionDomain receivedTransaction(@Payload String payload) throws IOException {
        TransactionDomain domain = null;
        try {
            JsonNode root = new ObjectMapper().readTree(payload);
            String messageBody = root.get("Message").asText();
            domain = objectMapper.readValue(messageBody, TransactionDomain.class);

            notificationClient.notification("cbarbosagomesfilho@gmail.com",
                    domain.getCodeTransaction().toString(), domain.getAmountTransaction());

        } catch (Exception e) {
            throw e;
        }
        return domain;
    }
}
