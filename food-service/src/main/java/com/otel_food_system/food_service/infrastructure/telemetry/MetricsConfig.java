package com.otel_food_system.food_service.infrastructure.telemetry;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfig {

    public MetricsConfig(MeterRegistry registry) {

        registry.config().commonTags(
                "service",
                "food-service"
        );
    }
}