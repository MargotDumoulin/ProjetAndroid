package com.example.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.td1.modele.Categorie;
import com.example.td1.modele.Produit;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int MAIN_VENTE = 0;
    private static final int MAIN_CATALOGUE = 1;

    private ArrayList<Categorie> listCategories;
    private ListView lvCategories;
    private Double basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
    }

    public void onStart() {
        super.onStart();
        this.lvCategories = this.findViewById(R.id.ca_liste);
        this.lvCategories.setOnItemClickListener(this);

        this.listCategories = new ArrayList<Categorie>();
        this.listCategories.add(new Categorie(0,"Pull","bear_hoodie"));
        this.listCategories.add(new Categorie(1,"Bonnet","bear_hoodie"));

        CategoriesAdapter adapter = new CategoriesAdapter(this, this.listCategories);

        this.lvCategories.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == MainActivity.RETOUR) {
            if (requestCode == MAIN_VENTE) {
                this.basket = intent.getDoubleExtra("basket", 0);
                this.updateBasket();
            } else if (requestCode == MAIN_CATALOGUE) {
                // ici, rien à faire si on revient du mode catalogue
            }
        } // on ne fait rien en cas d'annulation
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
        Intent intent = new Intent(CategoriesActivity.this, MainActivity.class);
        intent.putExtra("id_categ", this.listCategories.get(index).getId());
        startActivityForResult(intent, MAIN_VENTE);
    }

    public void updateBasket() {

    }
}