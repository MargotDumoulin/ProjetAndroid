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
import com.example.td1.modele.Client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomerDAO {

    public static void registerCustomer(Fragment fragment, JSONObject customerJson) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/clients/registerCustomer.php";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, customerJson,
                (Response.Listener<JSONObject>) fragment,
                (Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

    public static void updateCustomer(Fragment fragment, JSONObject customerJson) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~mackow1u/DevMob/php/clients/updateCustomer.php";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, customerJson,
                (Response.Listener<JSONObject>) fragment,
                (Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

    public static void isLoginInfoCorrect(Fragment fragment, JSONObject credentialsJson) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/clients/isLoginInfoCorrect.php";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, credentialsJson,
                (Response.Listener<JSONObject>) fragment,
                (Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

    public static void doesIdentifierAlreadyExist(Fragment fragment, String identifier) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/clients/doesIdentifierAlreadyExist.php?identifier=" + identifier;

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (Response.Listener<JSONObject>) fragment,
                (Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

    public static void getCustomerByIdentifier(Fragment fragment, String identifier) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/clients/findByIdentifier.php?identifier=" + identifier;

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (Response.Listener<JSONObject>) fragment,
                (Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }
}
