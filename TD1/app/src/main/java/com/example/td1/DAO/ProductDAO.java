package com.example.td1.DAO;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProductDAO {

    public static void findProduct(Fragment fragment, int index, int categ) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/findProduct.php?index=" + (index + 1) + "&categ=" + categ;

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) fragment,
                (Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

    public static void findAllByCateg(Fragment fragment, int categ, int idCustomer) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "";

        if (idCustomer == -1) { // user not connected
            url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/findAllByCateg.php?categ=" + categ;
        } else {
            url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/findAllByCateg.php?categ=" + categ + "&customer=" + idCustomer;
        }
        // Request a string response from the provided URL.
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) fragment,
                (Response.ErrorListener) fragment);


        // Add the request to the RequestQueue
        queue.add(jsonArray);
    }

    public static void findAllSizesByCateg(Fragment fragment, int categ) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/findAllSizesByCateg.php?categ=" + categ;

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) fragment,
                (Response.ErrorListener) fragment);


        // Add the request to the RequestQueue
        queue.add(jsonArray);
    }
    public static void findAllSizesByStarred(Fragment fragment, int idCustomer) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/findAllSizesByStarred.php?customer=" + idCustomer;

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) fragment,
                (Response.ErrorListener) fragment);


        // Add the request to the RequestQueue
        queue.add(jsonArray);
    }


    public static void getProductTableLength(Fragment fragment, int categ) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/productTableLength.php?categ=" + categ;

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONObject>) fragment,
                (Response.ErrorListener) fragment);


        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }

    public static void starProduct(Fragment fragment, int productId, int customerId) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/starProduct.php?product=" + productId + "&customer=" + customerId;

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) fragment,
                (Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonArray);
    }

    public static void unstarProduct(Fragment fragment, int productId, int customerId) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/unstarProduct.php?product=" + productId + "&customer=" + customerId;

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) fragment,
                (Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonArray);
    }

    public static void findAllStarred(Fragment fragment, int customerId) {
        RequestQueue queue = Volley.newRequestQueue(fragment.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/findAllStarred.php?customer=" + customerId;

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) fragment,
                (Response.ErrorListener) fragment);

        // Add the request to the RequestQueue
        queue.add(jsonArray);
    }
}
