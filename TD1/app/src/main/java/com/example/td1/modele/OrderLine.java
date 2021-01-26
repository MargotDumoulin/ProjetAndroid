package com.example.td1.modele;

import java.io.Serializable;

public class OrderLine implements Serializable {

    private String productDescription;
    private String size;
    private int quantity;
    private double price;

    public OrderLine(String productDescription, String size, int quantity, double price) {
        this.price = price;
        this.productDescription = productDescription;
        this.size = size;
        this.quantity = quantity;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
