package com.example.td1;

import com.example.td1.modele.Panier;

public interface ActiviteECommerce {
    Panier getBasket();

    void updateBasket(Panier basket);
}
