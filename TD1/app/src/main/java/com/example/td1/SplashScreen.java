package com.example.td1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.td1.DAO.CategorieDAO;
import com.example.td1.modele.Categorie;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Objects;

public class SplashScreen extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener {

    private ArrayList<Categorie> listCategories;
    final int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.listCategories = new ArrayList<>();
        new Handler().postDelayed(() -> CategorieDAO.findAll(this), SPLASH_TIME_OUT);
    }

    @Override
    public void onResponse(JSONArray response) {
        Intent intent = new Intent(SplashScreen.this, CategoriesActivity.class);
        intent.putExtra("categories", response.toString());
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "");
        Toast.makeText(this, R.string.error_bdd, Toast.LENGTH_LONG).show();
    }
}