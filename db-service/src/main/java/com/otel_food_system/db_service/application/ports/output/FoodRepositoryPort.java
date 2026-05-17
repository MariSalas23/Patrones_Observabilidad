package com.otel_food_system.db_service.application.ports.output;

import java.util.List;

import com.otel_food_system.db_service.domain.model.Food;

public interface FoodRepositoryPort {

    List<Food> getFoods();

    void insertFood();
}