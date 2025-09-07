package br.com.acme.adapters.output.kafka.producer;

import br.com.acme.application.domain.StatusTransaction;
import br.com.acme.application.domain.model.TransactionDomain;
import br.com.acme.application.mapper.ConverterMapper;
import br.com.acme.application.ports.out.IProducerEventsWalletTransaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerWalletTransactionEvent implements IProducerEventsWalletTransaction {

    private final String topic_success_transaction;
    private final String topic_success_transaction_failed;
    private final ConverterMapper converterMapper;
    private final KafkaTemplate<String, WalletsTransactionEvent> kafkaTemplate;

    public ProducerWalletTransactionEvent(
            @Value("${event.topic.success}") String topic_success_transaction,
            @Value("${event.topic.failed}")String topicSuccessTransactionFailed,
            ConverterMapper converterMapper,
            KafkaTemplate<String, WalletsTransactionEvent> kafkaTemplate) {

        this.topic_success_transaction = topic_success_transaction;
        this. topic_success_transaction_failed = topicSuccessTransactionFailed;
        this.converterMapper = converterMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendEventWalletTransaction(TransactionDomain transactionDomain) {
        if (transactionDomain.getStatusTransaction().equals(StatusTransaction.FAILED)) {
            this.kafkaTemplate.send(topic_success_transaction_failed, createWalletsTransactionEvent(transactionDomain));
        }else {
            this.kafkaTemplate.send(topic_success_transaction, createWalletsTransactionEvent(transactionDomain));
        }
    }

    private WalletsTransactionEvent createWalletsTransactionEvent(TransactionDomain domain) {
        return (WalletsTransactionEvent) this.converterMapper.convertObject(domain, WalletsTransactionEvent.class);
    }
}