package com.example.td1.modele;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Produit extends Base {

    protected int idCategorie;
    protected double price;
    protected String description;
    protected ArrayList<String> sizes;

    public Produit(int id, int idCategorie, double price, String imgSrc, String description, String title, ArrayList sizes) {
        super(id, imgSrc, title);
        this.idCategorie = idCategorie;
        this.price = price;
        this.description = description;
        this.sizes = sizes;
    }

    public int getIdCategorie() { return this.idCategorie; }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public ArrayList<String> getSizes() {
        return this.sizes;
    }

    public void setSizes(ArrayList sizes) {
        this.sizes = sizes;
    }

    public void addSize(String size) {
        this.sizes.add(size);
    }

}
