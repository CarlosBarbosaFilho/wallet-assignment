package br.com.acme.infrastructure.external.consumer;

import br.com.acme.config.NotificationClient;
import br.com.acme.infrastructure.external.consumer.response.TransactionDomain;
import br.com.acme.mapper.ConverterMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Random;

@Component
@AllArgsConstructor
public class ListenTransactionService implements ListenTransactionQueueSuccess {

    private final ConverterMapper converterMapper;
    private final NotificationClient notificationClient;

    @SqsListener("transactions-wallets")
    public TransactionDomain receivedTransaction(@Payload String payload) throws IOException {
        TransactionDomain domain = null;
        try {
            JsonNode root = new ObjectMapper().readTree(payload);
            String messageBody = root.get("Message").asText();
            domain = new ObjectMapper().readValue(messageBody, TransactionDomain.class);

            notificationClient.notification("cbarbosagomesfilho@gmail.com",
                    domain.getCodeTransaction().toString(), domain.getAmountTransaction());

            if(new Random().nextBoolean()){
                throw new RuntimeException("Error to send message ... : " + domain);
            }

        } catch (Exception e) {
            throw e;
        }
        return domain;
    }
}
