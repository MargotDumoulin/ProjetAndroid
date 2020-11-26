package com.example.td1.modele;

import java.io.Serializable;

public abstract class Base implements Serializable {

    protected int id;
    protected String title;
    protected String imgSrc;

    public Base(int id, String imgSrc, String title) {
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
