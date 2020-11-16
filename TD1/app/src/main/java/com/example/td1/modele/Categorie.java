package com.example.td1.modele;

import java.io.Serializable;

public class Categorie implements Serializable {

    private int id;
    private String title;
    private String imgSrc;

    public Categorie(int id, String title, String imgSrc) {
        this.id = id;
        this.imgSrc = imgSrc;
        this.title = title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getImgSrc() {
        return this.imgSrc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }


}
