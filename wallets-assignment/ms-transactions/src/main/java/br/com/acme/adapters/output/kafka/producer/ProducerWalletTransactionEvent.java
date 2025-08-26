package br.com.acme.adapters.output.kafka.producer;

import br.com.acme.application.domain.model.TransactionDomain;
import br.com.acme.application.mapper.ConverterMapper;
import br.com.acme.application.ports.out.IProducerEventsWalletTransaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerWalletTransactionEvent implements IProducerEventsWalletTransaction {

    private final String topic;
    private final ConverterMapper converterMapper;
    private final KafkaTemplate<String, WalletsTransactionEvent> kafkaTemplate;

    public ProducerWalletTransactionEvent(
            @Value("${event.topic}") String topic,
            ConverterMapper converterMapper,
            KafkaTemplate<String, WalletsTransactionEvent> kafkaTemplate) {

        this.topic = topic;
        this.converterMapper = converterMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendEventWalletTransaction(TransactionDomain transactionDomain) {
        this.kafkaTemplate.send(topic, createWalletsTransactionEvent(transactionDomain));
    }

    private WalletsTransactionEvent createWalletsTransactionEvent(TransactionDomain domain) {
        return (WalletsTransactionEvent) this.converterMapper.convertObject(domain, WalletsTransactionEvent.class);
    }
}