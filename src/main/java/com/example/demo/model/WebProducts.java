package com.example.demo.model;

public class WebProducts {
    private String id;

    private String name;

    private Double price;

    private Integer number;

    public WebProducts() {

    }

    public WebProducts(String id, String name, Double price, Integer number) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.number = number;
    }

    @Override
    public String toString() {
        return "WebProducts{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", number=" + number +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}