package com.example.td1.DAO;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.td1.modele.Panier;

import org.json.JSONObject;

public class OrderDAO {

    public static void registerOrder(Fragment activity, int customer) {

        RequestQueue queue = Volley.newRequestQueue(activity.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/registerOrder.php?customer=" + customer;

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) activity,
                (Response.ErrorListener) activity);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

    public static void registerOrderLine(Fragment activity, int order, int size, int quantity, double tarif, int product) {
        RequestQueue queue = Volley.newRequestQueue(activity.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/registerOrderLine.php?order=" + order + "&size=" + size + "&quantity=" + quantity + "&price=" + tarif + "&product=" + product;

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) activity,
                (Response.ErrorListener) activity);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }
}
