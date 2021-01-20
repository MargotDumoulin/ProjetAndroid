package com.example.td1.DAO;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MentionLegalesDAO {

    public static void findMension(Fragment activity, String language) {
        if (language.equals("en") || language.equals("fr")) {
            RequestQueue queue = Volley.newRequestQueue(activity.getContext());
            String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/mentions_legales/findByLanguage.php?lang=" + language;
            // Request a string response from the provided URL.
            JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null,
                    (com.android.volley.Response.Listener<JSONArray>) activity,
                    (Response.ErrorListener) activity);

            // Add the request to the RequestQueue
            queue.add(jsonArray);
        }
    }
}
