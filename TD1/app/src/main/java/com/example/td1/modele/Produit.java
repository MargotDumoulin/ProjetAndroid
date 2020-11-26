package com.example.td1.modele;

public class Produit extends Base {

    protected int idCategorie;
    protected double price;
    protected String description;

    public Produit(int id, int idCategorie, double price, String imgSrc, String description, String title) {
        super(id, imgSrc, title);
        this.idCategorie = idCategorie;
        this.price = price;
        this.description = description;
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

}
