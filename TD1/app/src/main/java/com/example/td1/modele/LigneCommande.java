package com.example.td1.modele;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LigneCommande {

    private int idOrder;
    private int idProduit;
    private int idSize;
    private int quantity;
    private double price;

    public LigneCommande(int idOrder, int idProduit, int idSize, int quantity, double price) {
        this.idOrder = idOrder;
        this.idProduit = idProduit;
        this.idSize = idSize;
        this.price = price;
        this.quantity = quantity;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdSize() {
        return idSize;
    }

    public void setIdSize(int idSize) {
        this.idSize = idSize;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product", getIdProduit());
            jsonObject.put("order", getIdOrder());
            jsonObject.put("size", getIdSize());
            jsonObject.put("quantity", getQuantity());
            jsonObject.put("price", getPrice());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
