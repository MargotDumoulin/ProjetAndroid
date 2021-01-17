package com.example.td1.modele;

import java.io.Serializable;

public class Taille {

    private int id;
    private String label;

    public Taille(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setLabel(String imgSrc) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

}
