package br.com.acme.adapters.output.lambda;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

@Service
@AllArgsConstructor
public class ReprocessTransaction implements ReprocessTransactionsDQL {

    private static final Logger logger = LoggerFactory.getLogger(ReprocessTransaction.class);
    private final LambdaClient lambdaClient;

    @Override
    @Scheduled(fixedDelay = 60000)
    public String invokeReprocess() {
        logger.info("Scheduler active. Invoke the lambda 'dlq-reprocess-transactions'...");

        try {
            InvokeRequest request = InvokeRequest.builder()
                    .functionName("dlq-reprocess-transactions")
                    .payload(SdkBytes.fromUtf8String("{}"))
                    .build();

            InvokeResponse response = lambdaClient.invoke(request);
            String payload = response.payload().asUtf8String();

            logger.info("Lambda invoked successfully! Response: {}", payload);
            return payload;

        } catch (Exception e) {
            logger.error("Error to invoke  Lambda!", e);
            return "Error invocation lambda: " + e.getMessage();
        }
    }
}