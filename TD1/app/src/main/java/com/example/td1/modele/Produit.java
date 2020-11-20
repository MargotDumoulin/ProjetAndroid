package com.example.td1.modele;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;

import com.example.td1.R;

import java.io.Serializable;
import java.util.ArrayList;

public class Produit implements Serializable {

    private int id;
    private int idCategorie;
    private double price;
    private String imgSrc;
    private String description;
    private String title;

    public Produit(int id, int idCategorie, int price, String imgSrc, String description, String title) {
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

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public int getIdCategorie() {
        return this.idCategorie;
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
