package com.example.td1.modele;

import java.io.Serializable;

public class Categorie extends Base implements Serializable  {

    public Categorie(int id, String title, String imgSrc) {
        super(id, imgSrc, title);
    }
}
