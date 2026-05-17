package com.otel_food_system.db_service.infrastructure.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otel_food_system.db_service.infrastructure.repository.JdbcFoodRepository;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;

@RestController
public class DbController {

    private final JdbcFoodRepository repository;
    private final Tracer tracer;

    public DbController(
            JdbcFoodRepository repository,
            Tracer tracer
    ) {

        this.repository = repository;
        this.tracer = tracer;
    }

    @GetMapping("/db/comidas")
    public String getFoods() {

        Span span =
                tracer.spanBuilder("db-controller-get-comidas")
                        .startSpan();

        try {

            long inicio = System.currentTimeMillis();

            System.out.println("REQUEST ENTRÓ A DB-SERVICE");

            System.out.println("ENDPOINT EJECUTADO: /db/comidas");

            String response = repository.getFoods().toString();

            long fin = System.currentTimeMillis();

            System.out.println(
                    "TIEMPO REQUEST DB-SERVICE: "
                            + (fin - inicio)
                            + " ms"
            );

            return response;

        } catch (Exception e) {

            span.recordException(e);
            span.setStatus(StatusCode.ERROR);

            System.out.println(
                    "ERROR EN DB-CONTROLLER GET: "
                            + e.getMessage()
            );

            throw e;

        } finally {

            span.end();
        }
    }

    @PostMapping("/db/insertar")
    public String insertFood() {

        Span span =
                tracer.spanBuilder("db-controller-insert-comidas")
                        .startSpan();

        try {

            long inicio = System.currentTimeMillis();

            System.out.println("REQUEST INSERT EN DB-SERVICE");

            System.out.println("ENDPOINT EJECUTADO: /db/insertar");

            repository.insertFood();

            long fin = System.currentTimeMillis();

            System.out.println(
                    "TIEMPO INSERT DB-SERVICE: "
                            + (fin - inicio)
                            + " ms"
            );

            return "Comida insertada";

        } catch (Exception e) {

            span.recordException(e);
            span.setStatus(StatusCode.ERROR);

            System.out.println(
                    "ERROR EN DB-CONTROLLER INSERT: "
                            + e.getMessage()
            );

            throw e;

        } finally {

            span.end();
        }
    }
}