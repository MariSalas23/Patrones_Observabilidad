package com.otel_food_system.db_service.domain.model;

public class Food {

    private Long id;
    private String nombre;
    private int calorias;

    public Food(Long id, String nombre, int calorias) {
        this.id = id;
        this.nombre = nombre;
        this.calorias = calorias;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCalorias() {
        return calorias;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", calorias=" + calorias +
                '}';
    }
}