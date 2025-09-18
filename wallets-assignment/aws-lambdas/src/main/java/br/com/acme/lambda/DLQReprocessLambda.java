package br.com.acme.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import java.net.URI;

public class DLQReprocessLambda implements RequestHandler<Object, String> {

    private final Logger logger = LoggerFactory.getLogger(DLQReprocessLambda.class);

    private final  SqsClient sqs = SqsClient.builder()
            .endpointOverride(URI.create("http://localstack:4566"))
    //endpointOverride(URI.create("http://host.docker.internal:4566"))
            .region(software.amazon.awssdk.regions.Region.US_EAST_1)
            .credentialsProvider(StaticCredentialsProvider.create(
                    AwsBasicCredentials.create("cbgomes","cbgomes")
            ))
            .build();

    @Override
    public String handleRequest(Object object, Context context) {
        String dlqUrl = "http://host.docker.internal:4566/000000000000/transactions-wallets-dlq";
        String targetUrl = "http://host.docker.internal:4566/000000000000/transactions-wallets";

        ReceiveMessageResponse resp = sqs.receiveMessage(r -> r
                .queueUrl(dlqUrl)
                .maxNumberOfMessages(10)
        );

        for (Message message : resp.messages()) {
            sqs.sendMessage(b -> b.queueUrl(targetUrl).messageBody(message.body()));
            sqs.deleteMessage(d -> d.queueUrl(dlqUrl).receiptHandle(message.receiptHandle()));
            logger.info("Transactions reprocessed :: {}", message.body());
        }

        return "Reprocessing is complete";
    }
}
