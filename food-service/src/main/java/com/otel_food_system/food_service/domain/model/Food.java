package com.otel_food_system.food_service.domain.model;

public class Food {

    private Integer id;
    private String nombre;
    private Integer calorias;

    public Food() {
    }

    public Food(Integer id, String nombre, Integer calorias) {
        this.id = id;
        this.nombre = nombre;
        this.calorias = calorias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }
}