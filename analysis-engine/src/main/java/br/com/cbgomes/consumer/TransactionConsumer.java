package br.com.cbgomes.consumer;


import br.com.cbgomes.domain.TransactionDomain;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class TransactionConsumer {

    private final ObjectMapper mapper;

    public TransactionConsumer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @KafkaListener(topics = "events-wallets-transaction", groupId = "consumer-events-wallets-transactions-success-replay-v1")
    public void consume(String message) {
        try {
            TransactionDomain tx = mapper.readValue(message, TransactionDomain.class);
            log.info("Transaction Received: {}", tx);
        } catch (Exception e) {
            log.error("Error processing transaction", e);
        }
    }
}
