package com.otel_food_system.db_service.infrastructure.telemetry;

import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class MetricsConfig {

    public MetricsConfig(MeterRegistry registry) {

        registry.config().commonTags(
                "service",
                "db-service"
        );
    }
}