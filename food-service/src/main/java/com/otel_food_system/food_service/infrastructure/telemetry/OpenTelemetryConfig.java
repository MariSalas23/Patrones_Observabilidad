package com.otel_food_system.food_service.infrastructure.telemetry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.sdk.OpenTelemetrySdk;

@Configuration
public class OpenTelemetryConfig {

    @Bean
    public OpenTelemetry openTelemetry() {

        return OpenTelemetrySdk.builder().build();
    }

    @Bean
    public Tracer tracer(OpenTelemetry openTelemetry) {

        return openTelemetry.getTracer("food-service");
    }
}