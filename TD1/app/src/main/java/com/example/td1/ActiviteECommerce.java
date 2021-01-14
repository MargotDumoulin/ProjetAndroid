package com.example.td1;

import com.example.td1.modele.Panier;

public interface ActiviteECommerce {
    Panier getPanier();

    void updatePanier(Panier basket);

    void updatePanierPrix(double a);

    double getPanierPrix();
}
