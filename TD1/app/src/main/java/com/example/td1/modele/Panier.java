package com.example.td1.modele;

import com.example.td1.utils.Triplet;

import java.io.Serializable;
import java.util.ArrayList;

public class Panier implements Serializable {
    private ArrayList<Triplet<Integer, String, String>> basketContent; // using Tuples to create a Triplet with Triplet<idArticle, size, color> required

    public Panier(ArrayList<Triplet<Integer, String, String>> basketContent) {
        this.basketContent = basketContent;
    }

    public void addArticle(Integer idArticle, String size, String color) {
        this.basketContent.add(Triplet.of(idArticle, size, color));
    }

    public void removeArticle(int index) {
        this.basketContent.remove(index);
    }

    public void removeAllArticles() {
        this.basketContent.clear();
    }

    public int getBasketSize() {
        return this.basketContent.size();
    }

    public Triplet<Integer, String, String> getArticle(int index) {
        return this.basketContent.get(index);
    }

    public ArrayList<Triplet<Integer, String, String>> getBasketContent() {
        return this.basketContent;
    }
}
