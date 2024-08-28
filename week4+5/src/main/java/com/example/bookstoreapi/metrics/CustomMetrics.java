package main.java.com.example.bookstoreapi.metrics;

import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomMetrics {

    @Bean
    public CustomMetrics customMetrics(MeterRegistry meterRegistry) {
        meterRegistry.counter("custom_metric", "type", "example");
        return new CustomMetrics();
    }
}
