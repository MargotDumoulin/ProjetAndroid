package com.example.td1.modele;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;

import com.example.td1.R;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Produit implements Serializable {

    protected int id;
    protected int idCategorie;
    protected double price;
    protected String imgSrc;
    protected String description;
    protected String title;

    public Produit(int id, int idCategorie, double price, String imgSrc, String description, String title) {
        this.id = id;
        this.idCategorie = idCategorie;
        this.price = price;
        this.imgSrc = imgSrc;
        this.description = description;
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
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

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getImgSrc() {
        return this.imgSrc;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
