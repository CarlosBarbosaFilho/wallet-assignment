package br.com.acme.usecases;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.InputLogEvent;
import software.amazon.awssdk.services.cloudwatchlogs.model.PutLogEventsRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.PutLogEventsResponse;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CloudWatchLogService {

    private final CloudWatchLogsClient cloudWatchLogsClient;

    public void sendLog(String message) {
        InputLogEvent logEvent = InputLogEvent.builder()
                .message(message)
                .timestamp(Instant.now().toEpochMilli())
                .build();

        PutLogEventsRequest.Builder requestBuilder = PutLogEventsRequest.builder()
                .logGroupName("ms-transactions-logs")
                .logStreamName("transactions-stream")
                .logEvents(List.of(logEvent));


        PutLogEventsResponse response = cloudWatchLogsClient.putLogEvents(requestBuilder.build());
    }
}
