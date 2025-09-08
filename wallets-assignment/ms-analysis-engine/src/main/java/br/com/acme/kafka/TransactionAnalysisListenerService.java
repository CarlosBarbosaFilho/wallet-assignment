package br.com.acme.kafka;

import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TransactionAnalysisListenerService implements ConsumerSeekAware {

    @KafkaListener(topics = "events-wallets-transaction",
            groupId = "consumer-events-wallets-transactions-success-replay")
    public void listen(WalletsTransactionEvent event) {
        System.out.println("Consumed event: " + event);
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        assignments.keySet().forEach(tp -> callback.seekToBeginning(tp.topic(), tp.partition()));
    }
}
