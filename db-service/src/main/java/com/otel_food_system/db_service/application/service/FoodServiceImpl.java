package com.otel_food_system.db_service.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.otel_food_system.db_service.application.ports.input.GetFoodsUseCase;
import com.otel_food_system.db_service.application.ports.output.FoodRepositoryPort;
import com.otel_food_system.db_service.domain.model.Food;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;

@Service
public class FoodServiceImpl implements GetFoodsUseCase {

    private final FoodRepositoryPort repository;
    private final Tracer tracer;

    public FoodServiceImpl(
            FoodRepositoryPort repository,
            Tracer tracer
    ) {

        this.repository = repository;
        this.tracer = tracer;
    }

    @Override
    public List<Food> getFoods() {

        Span span =
                tracer.spanBuilder("db-service-process-request")
                        .startSpan();

        try {

            long inicio = System.currentTimeMillis();

            System.out.println("REQUEST ENTRÓ A DB-SERVICE");

            System.out.println("Consultando comidas");

            List<Food> foods = repository.getFoods();

            long fin = System.currentTimeMillis();

            System.out.println(
                    "TIEMPO REQUEST DB-SERVICE: "
                            + (fin - inicio)
                            + " ms"
            );

            return foods;

        } catch (Exception e) {

            span.recordException(e);
            span.setStatus(StatusCode.ERROR);

            System.out.println(
                    "ERROR EN DB-SERVICE: "
                            + e.getMessage()
            );

            throw e;

        } finally {

            span.end();
        }
    }
}