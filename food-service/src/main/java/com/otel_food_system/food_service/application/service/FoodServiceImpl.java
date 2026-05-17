package com.otel_food_system.food_service.application.service;

import org.springframework.stereotype.Service;

import com.otel_food_system.food_service.application.ports.input.GetFoodsUseCase;
import com.otel_food_system.food_service.application.ports.output.FoodClientPort;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;

@Service
public class FoodServiceImpl implements GetFoodsUseCase {

    private final FoodClientPort foodClientPort;
    private final Tracer tracer;

    public FoodServiceImpl(
            FoodClientPort foodClientPort,
            Tracer tracer
    ) {

        this.foodClientPort = foodClientPort;
        this.tracer = tracer;
    }

    @Override
    public String getFoods() {

        Span span =
                tracer.spanBuilder("food-service-process-request")
                        .startSpan();

        try {

            long inicio = System.currentTimeMillis();

            System.out.println("REQUEST ENTRÓ A FOOD-SERVICE");

            System.out.println("ENDPOINT EJECUTADO: /comidas");

            System.out.println("Procesando request GET");

            System.out.println("FOOD-SERVICE LLAMANDO DB-SERVICE");

            String response = foodClientPort.getFoodsFromDbService();

            long fin = System.currentTimeMillis();

            System.out.println(
                    "TIEMPO REQUEST FOOD-SERVICE: "
                            + (fin - inicio)
                            + " ms"
            );

            return response;

        } catch (Exception e) {

            span.recordException(e);
            span.setStatus(StatusCode.ERROR);

            System.out.println(
                    "ERROR EN FOOD-SERVICE: "
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
                tracer.spanBuilder("food-service-insert-request")
                        .startSpan();

        try {

            long inicio = System.currentTimeMillis();

            System.out.println("REQUEST INSERT EN FOOD-SERVICE");

            System.out.println("ENDPOINT EJECUTADO: /insertar");

            System.out.println("Procesando INSERT");

            System.out.println("FOOD-SERVICE LLAMANDO DB-SERVICE PARA INSERT");

            String response = foodClientPort.insertFood();

            long fin = System.currentTimeMillis();

            System.out.println(
                    "TIEMPO INSERT FOOD-SERVICE: "
                            + (fin - inicio)
                            + " ms"
            );

            return response;

        } catch (Exception e) {

            span.recordException(e);
            span.setStatus(StatusCode.ERROR);

            System.out.println(
                    "ERROR INSERT FOOD-SERVICE: "
                            + e.getMessage()
            );

            throw e;

        } finally {

            span.end();
        }
    }
}