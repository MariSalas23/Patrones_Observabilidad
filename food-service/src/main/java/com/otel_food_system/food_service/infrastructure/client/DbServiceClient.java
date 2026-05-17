package com.otel_food_system.food_service.infrastructure.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.otel_food_system.food_service.application.ports.output.FoodClientPort;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;

@Component
public class DbServiceClient implements FoodClientPort {

    private final Tracer tracer;

    public DbServiceClient(Tracer tracer) {

        this.tracer = tracer;
    }

    @Override
    public String getFoodsFromDbService() {

        Span span =
                tracer.spanBuilder("food-service-call-db-service")
                        .startSpan();

        try {

            long inicio = System.currentTimeMillis();

            System.out.println("FOOD-SERVICE LLAMANDO DB-SERVICE");

            System.out.println("REQUEST HACIA: http://db-service:8081/db/comidas");

            RestTemplate restTemplate = new RestTemplate();

            String response = restTemplate.getForObject(
                    "http://db-service:8081/db/comidas",
                    String.class
            );

            long fin = System.currentTimeMillis();

            System.out.println(
                    "TIEMPO LLAMADO DB-SERVICE: "
                            + (fin - inicio)
                            + " ms"
            );

            return response;

        } catch (Exception e) {

            span.recordException(e);
            span.setStatus(StatusCode.ERROR);

            System.out.println(
                    "ERROR LLAMANDO DB-SERVICE: "
                            + e.getMessage()
            );

            throw e;

        } finally {

            span.end();
        }
    }

    @Override
    public String insertFood() {

        Span span =
                tracer.spanBuilder("food-service-call-db-service-insert")
                        .startSpan();

        try {

            long inicio = System.currentTimeMillis();

            System.out.println("FOOD-SERVICE LLAMANDO DB-SERVICE PARA INSERT");

            System.out.println("REQUEST HACIA: http://db-service:8081/db/insertar");

            RestTemplate restTemplate = new RestTemplate();

            String response = restTemplate.postForObject(
                    "http://db-service:8081/db/insertar",
                    null,
                    String.class
            );

            long fin = System.currentTimeMillis();

            System.out.println(
                    "TIEMPO INSERT DB-SERVICE: "
                            + (fin - inicio)
                            + " ms"
            );

            return response;

        } catch (Exception e) {

            span.recordException(e);
            span.setStatus(StatusCode.ERROR);

            System.out.println(
                    "ERROR INSERT DB-SERVICE: "
                            + e.getMessage()
            );

            throw e;

        } finally {

            span.end();
        }
    }
}