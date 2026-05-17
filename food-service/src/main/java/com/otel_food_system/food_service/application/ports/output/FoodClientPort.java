package com.otel_food_system.food_service.application.ports.output;

public interface FoodClientPort {

    String getFoodsFromDbService();

    String insertFood();
}