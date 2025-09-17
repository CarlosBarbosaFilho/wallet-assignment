package br.com.acme.kafka;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@AllArgsConstructor
public class TransactionAnalysisListenerService implements ConsumerSeekAware {
    private final Counter processedEventsCounter;
    private final Timer eventProcessingTimer;
    private final MeterRegistry registry;


    @KafkaListener(topics = "events-wallets-transaction", groupId = "consumer-events-wallets-transactions-success-replay")
    public void listen(WalletsTransactionEvent event) {
            System.out.println("Event processed: " + event);
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        assignments.keySet().forEach(tp -> callback.seekToBeginning(tp.topic(), tp.partition()));
    }
}
