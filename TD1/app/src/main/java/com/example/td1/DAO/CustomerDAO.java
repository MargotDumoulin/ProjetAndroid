package com.example.td1.DAO;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.td1.modele.Client;

import org.json.JSONArray;

public class CustomerDAO {

    public static void regsiterCustomer(Context activity, Client customer) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/clients/registerCustomer.php";

        // Request a string response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                (Response.Listener<JSONArray>) activity,
                (Response.ErrorListener) activity);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }
}
