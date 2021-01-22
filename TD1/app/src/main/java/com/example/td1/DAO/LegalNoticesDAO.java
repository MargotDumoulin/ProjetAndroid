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

public class LegalNoticesDAO {

    public static void getLegalNoticesByLang(Fragment activity, String language) {

        RequestQueue queue = Volley.newRequestQueue(activity.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/mentions_legales/findByLanguage.php?lang=" + language;
        // Request a string response from the provided URL.
        JsonObjectRequest jsonObject = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) activity,
                (Response.ErrorListener) activity);

        // Add the request to the RequestQueue
        queue.add(jsonObject);
    }
}
