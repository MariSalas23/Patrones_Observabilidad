package com.otel_food_system.food_service.infrastructure.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otel_food_system.food_service.application.ports.input.GetFoodsUseCase;

@RestController
public class FoodController {

    private final GetFoodsUseCase useCase;

    public FoodController(GetFoodsUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/comidas")
    public String getFoods() {

        long inicio = System.currentTimeMillis();

        System.out.println("REQUEST ENTRÓ A FOOD-SERVICE");

        String response = useCase.getFoods();

        long fin = System.currentTimeMillis();

        System.out.println("TIEMPO REQUEST FOOD-SERVICE: " + (fin - inicio) + " ms");

        return response;
    }

    @PostMapping("/insertar")
    public String insertFood() {

        System.out.println("Insert recibido en food-service");

        return useCase.insertFood();
    }
}