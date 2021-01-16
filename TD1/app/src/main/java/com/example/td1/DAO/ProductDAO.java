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

    public static void findAllByCateg(Fragment activity, int categ, int idCustomer) {
        RequestQueue queue = Volley.newRequestQueue(activity.getContext());
        String url = "";

        if (idCustomer == -1) { // user not connected
            url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/findAllByCateg.php?categ=" + categ;
        } else {
            url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/findAllByCateg.php?categ=" + categ + "&customer=" + idCustomer;
        }
        // Request a string response from the provided URL.
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) activity,
                (Response.ErrorListener) activity);


        // Add the request to the RequestQueue
        queue.add(jsonArray);
    }

    public static void findAllSizesByCateg(Fragment activity, int categ) {
        RequestQueue queue = Volley.newRequestQueue(activity.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/findAllSizesByCateg.php?categ=" + categ;

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) activity,
                (Response.ErrorListener) activity);


        // Add the request to the RequestQueue
        queue.add(jsonArray);
    }
    public static void findAllSizesByStarred(Fragment activity, int idCustomer) {
        RequestQueue queue = Volley.newRequestQueue(activity.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/findAllSizesByStarred.php?customer=" + idCustomer;

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) activity,
                (Response.ErrorListener) activity);


        // Add the request to the RequestQueue
        queue.add(jsonArray);
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

    public static void starProduct(Fragment activity, int productId, int customerId) {
        RequestQueue queue = Volley.newRequestQueue(activity.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/starProduct.php?product=" + productId + "&customer=" + customerId;

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) activity,
                (Response.ErrorListener) activity);

        // Add the request to the RequestQueue
        queue.add(jsonArray);
    }

    public static void unstarProduct(Fragment activity, int productId, int customerId) {
        RequestQueue queue = Volley.newRequestQueue(activity.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/unstarProduct.php?product=" + productId + "&customer=" + customerId;

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) activity,
                (Response.ErrorListener) activity);

        // Add the request to the RequestQueue
        queue.add(jsonArray);
    }

    public static void findAllStarred(Fragment activity, int customerId) {
        RequestQueue queue = Volley.newRequestQueue(activity.getContext());
        String url = "https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/PHP/produits/findAllStarred.php?customer=" + customerId;

        // Request a string response from the provided URL.
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null,
                (com.android.volley.Response.Listener<JSONArray>) activity,
                (Response.ErrorListener) activity);

        // Add the request to the RequestQueue
        queue.add(jsonArray);
    }
}
