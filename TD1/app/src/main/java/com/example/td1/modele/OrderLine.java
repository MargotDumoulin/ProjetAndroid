package com.example.td1.modele;

import java.io.Serializable;

public class OrderLine implements Serializable {

    private String productTitle;
    private String size;
    private int quantity;
    private double price;

    public OrderLine(String productTitle, String size, int quantity, double price) {
        this.price = price;
        this.productTitle = productTitle;
        this.size = size;
        this.quantity = quantity;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
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
