package br.com.acme.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
public class SnsConfig {

    @Value("${config.url-docker-localstack}")
    private String urlLocalstack;

    @Bean
    public SnsClient snsClient() {
        return SnsClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(java.net.URI.create(urlLocalstack))
                .credentialsProvider(() -> software.amazon.awssdk.auth.credentials.AwsBasicCredentials
                        .create("cbgomes", "cbgomes"))
                .build();
    }
}