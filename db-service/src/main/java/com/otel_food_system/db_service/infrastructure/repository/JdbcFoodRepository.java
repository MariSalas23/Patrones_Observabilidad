package com.otel_food_system.db_service.infrastructure.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.otel_food_system.db_service.application.ports.output.FoodRepositoryPort;
import com.otel_food_system.db_service.domain.model.Food;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;

@Repository
public class JdbcFoodRepository implements FoodRepositoryPort {

    private final JdbcTemplate jdbcTemplate;
    private final Tracer tracer;

    public JdbcFoodRepository(
            JdbcTemplate jdbcTemplate,
            Tracer tracer
    ) {

        this.jdbcTemplate = jdbcTemplate;
        this.tracer = tracer;
    }

    @Override
    public List<Food> getFoods() {

        System.out.println("CONSULTANDO BASE DE DATOS");

        Span span =
                tracer.spanBuilder("db-query-select-comidas")
                        .startSpan();

        try {

            long inicio = System.currentTimeMillis();

            List<Food> foods = jdbcTemplate.query(
                    "SELECT * FROM comidas",
                    (rs, rowNum) -> new Food(
                            rs.getLong("id"),
                            rs.getString("nombre"),
                            rs.getInt("calorias")
                    )
            );

            long fin = System.currentTimeMillis();

            System.out.println("TIEMPO DB: " + (fin - inicio) + " ms");

            return foods;

        } catch (Exception e) {

            span.recordException(e);
            span.setStatus(StatusCode.ERROR);

            System.out.println("ERROR EN CONSULTA DB: " + e.getMessage());

            throw e;

        } finally {

            span.end();
        }
    }

    @Override
    public void insertFood() {

        System.out.println("INSERTANDO EN BASE DE DATOS");

        Span span =
                tracer.spanBuilder("db-query-insert-comidas")
                        .startSpan();

        try {

            long inicio = System.currentTimeMillis();

            jdbcTemplate.update(
                    "INSERT INTO comidas(nombre, calorias) VALUES (?, ?)",
                    "Hot Dog",
                    900
            );

            long fin = System.currentTimeMillis();

            System.out.println("TIEMPO INSERT DB: " + (fin - inicio) + " ms");

        } catch (Exception e) {

            span.recordException(e);
            span.setStatus(StatusCode.ERROR);

            System.out.println("ERROR INSERT DB: " + e.getMessage());

            throw e;

        } finally {

            span.end();
        }
    }
}