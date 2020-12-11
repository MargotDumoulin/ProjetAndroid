package com.example.td1.DAO;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ProductDAO {

    public static void findProduct(Context activity, int index, int categ) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/findProduct.php?index=" + (index + 1) + "&categ=" + categ;

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) activity,
                (Response.ErrorListener) activity);


        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

    public static void getProductTableLength(Context activity, int categ) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/productTableLength.php?categ=" + categ;

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) activity,
                (Response.ErrorListener) activity);


        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }
}
