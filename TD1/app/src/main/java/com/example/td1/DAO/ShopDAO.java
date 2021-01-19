package com.example.td1.DAO;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class ShopDAO {

    public static void findAll(Fragment activity) {
        RequestQueue queue = Volley.newRequestQueue(activity.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~mackow1u/DevMob/php/magasins/findAll.php";

        // Request a string response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                (Response.Listener<JSONArray>) activity,
                (Response.ErrorListener) activity);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

}