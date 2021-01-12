package com.example.td1;

import com.example.td1.modele.Panier;

interface InterfaceECommerce {
    Panier getPanier();
    void updatePanier(Panier basket);
    void updatePanierPrix(double a);
    double getPanierPrix();
}
