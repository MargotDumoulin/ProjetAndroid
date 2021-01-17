package com.example.td1.modele;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.td1.utils.Triplet;
import java.io.Serializable;
import java.util.ArrayList;

public class Panier implements Serializable {
    private ArrayList<Triplet<Produit, Taille, Integer>> basketContent; // using Tuples to create a Triplet with Triplet<idArticle, size, quantity> required

    public Panier(ArrayList<Triplet<Produit, Taille, Integer>> basketContent) {
        this.basketContent = basketContent;
    }

    public void addArticle(Produit product, Taille size, Integer quantity) {
        this.basketContent.add(Triplet.of(product, size, quantity));
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

    public Triplet<Produit, Taille, Integer> getArticle(int index) {
        return this.basketContent.get(index);
    }

    public ArrayList<Triplet<Produit, Taille, Integer>> getBasketContent() {
        return this.basketContent;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public double getBasketTotal() {
        return this.basketContent
                .stream()
                .map(product -> product.first.getPrice() * product.third)
                .reduce(0.0, (subtotal, element) -> subtotal + element);

    }
}
