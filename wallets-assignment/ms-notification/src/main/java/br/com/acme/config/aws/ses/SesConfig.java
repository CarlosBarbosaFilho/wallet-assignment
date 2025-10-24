package br.com.acme.config.aws.ses;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

import java.net.URI;

@Configuration
public class SesConfig {

    @Bean
    public SesClient sesClient() {
        return SesClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create("http://localhost:4566")) // DEV
                //.endpointOverride(URI.create("http://localstack:4566")) // HML
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("cbgomes", "cbgomes")
                        )
                )
                .build();
    }
}