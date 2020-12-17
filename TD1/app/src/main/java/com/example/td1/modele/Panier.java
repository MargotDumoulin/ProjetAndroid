package com.example.td1.modele;

import com.example.td1.utils.Paired;
import java.io.Serializable;
import java.util.ArrayList;

public class Panier implements Serializable {
    private ArrayList<Paired<Integer, String>> basketContent; // using Tuples to create a Triplet with Triplet<idArticle, size, color> required

    public Panier(ArrayList<Paired<Integer, String>> basketContent) {
        this.basketContent = basketContent;
    }

    public void addArticle(Integer idArticle, String size) {
        this.basketContent.add(Paired.of(idArticle, size));
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

    public Paired<Integer, String> getArticle(int index) {
        return this.basketContent.get(index);
    }

    public ArrayList<Paired<Integer, String>> getBasketContent() {
        return this.basketContent;
    }
}
