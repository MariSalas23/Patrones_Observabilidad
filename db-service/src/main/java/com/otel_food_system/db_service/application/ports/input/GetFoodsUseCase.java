package com.otel_food_system.db_service.application.ports.input;

import java.util.List;

import com.otel_food_system.db_service.domain.model.Food;

public interface GetFoodsUseCase {

    List<Food> getFoods();
}