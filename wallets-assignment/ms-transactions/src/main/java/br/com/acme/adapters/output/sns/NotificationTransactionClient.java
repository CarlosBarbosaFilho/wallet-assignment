package br.com.acme.adapters.output.sns;

import br.com.acme.application.domain.model.TransactionDomain;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Service
public class NotificationTransactionClient implements NotificationTransactionsClient {

    private final SnsClient notificationTransactionService;
    private final ObjectMapper objectMapper;
    private final String topicArn;

    public NotificationTransactionClient(
            SnsClient notificationTransactionService,
            ObjectMapper objectMapper,
            @Value("${sns.topic-arn-transaction}") String topicArn) {

        this.notificationTransactionService = notificationTransactionService;
        this.objectMapper = objectMapper;
        this.topicArn = topicArn;
    }


    @Override
    public void sendNotificationTransaction(TransactionDomain transactionDomain) {
        try{
            var  messageJson = objectMapper.writeValueAsString(transactionDomain);

            PublishRequest request = PublishRequest.builder()
                    .topicArn(topicArn)
                    .message(messageJson)
                    .build();

            notificationTransactionService.publish(request);
            System.out.println("Message published with success.");
        }catch (Exception ex) {
            throw  new RuntimeException("Error send message to SNS topic - Transactions-Wallets");
        }
    }
}
