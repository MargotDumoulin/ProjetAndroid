package com.example.td1.modele;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.td1.R;
import java.util.ArrayList;

public class Pull  {
    int price;
    String imgSrc;
    String description;
    String title;

    public Pull(int price, String imgSrc, String description, String title) {
        this.price = price;
        this.imgSrc = imgSrc;
        this.description = description;
        this.title = title;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
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
