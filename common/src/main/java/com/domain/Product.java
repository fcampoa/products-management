package com.domain;

import java.io.Serializable;
public class Product implements Serializable {
    private int id;
    private String name;
    private float price;
    private String brand;
    private String barCode;

    public Product(int id) {
        this.id = id;
    }
    public Product() {
    }
    public Product(int id, String name, float price, String brand, String barCode) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.barCode = barCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}