package br.com.acme.application.usecases;

import br.com.acme.application.ports.out.CreateLogsCloudWatch;
import br.com.acme.utils.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.InputLogEvent;
import software.amazon.awssdk.services.cloudwatchlogs.model.PutLogEventsRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.PutLogEventsResponse;

import java.time.Instant;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class CloudWatchLogService implements CreateLogsCloudWatch {

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
