package br.com.acme;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationAnalysisEngineService {
    private final Counter actionCounter;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationAnalysisEngineService.class, args);
    }

    public ApplicationAnalysisEngineService(MeterRegistry registry) {
        this.actionCounter = Counter.builder("demo_actions_total")
                .description("Número total de ações executadas")
                .register(registry);
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", "bank-transactions-service");
    }
}