package com.example.td1.modele;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;

import com.example.td1.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Produit extends Base implements Serializable {

    protected int id;
    protected int idCategorie;
    protected double price;
    protected String imgSrc;
    protected String description;
    protected String title;

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
