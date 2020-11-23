package com.example.td1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.td1.modele.Categorie;
import com.example.td1.modele.Produit;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int MAIN_VENTE = 0;
    private static final int MAIN_CATALOGUE = 1;

    private ListView lvCategories;
    private RadioButton sellRadioButton;
    private RadioButton catalogRadioButton;
    private TextView totalTextView;
    private int modeSelected;
    private ArrayList<Produit> basket;
    private ArrayList<Categorie> listCategories;
    private double basketAmount;

    public static final int RETOUR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        if (savedInstanceState != null) {
            Log.d("wtf", "c'est ici?");
            this.basket = (ArrayList<Produit>) savedInstanceState.getSerializable("basket");
            Log.d("wtf", "c'est là?");
            this.basketAmount = savedInstanceState.getDouble("basketAmount");
        } else {
            this.basket = new ArrayList<Produit>();
            this.basketAmount = 0;
        }

        this.listCategories = new ArrayList<Categorie>();
        this.listCategories.add(new Categorie(0,"Pull","bear_hoodie"));
        this.listCategories.add(new Categorie(1,"Bonnet","bonnet"));

    }

    public void onStart() {
        super.onStart();
        this.lvCategories = this.findViewById(R.id.categoriesListView);
        this.lvCategories.setOnItemClickListener(this);

        CategoriesAdapter adapter = new CategoriesAdapter(this, this.listCategories);
        this.lvCategories.setAdapter(adapter);

        this.sellRadioButton = this.findViewById(R.id.sellRadioButton);
        this.catalogRadioButton = this.findViewById(R.id.catalogRadioButton);

        this.totalTextView = this.findViewById(R.id.totalTextView);
    }

    public void addProduct(View v) {
        Intent intent = new Intent(CategoriesActivity.this, NewProductActivity.class);
        startActivity(intent);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (this.basket.size() > 0) {
            outState.putDouble("basketAmount", this.basketAmount);
            outState.putSerializable("basket", this.basket);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == MainActivity.RETOUR) {
            if (requestCode == MAIN_VENTE) {
                ArrayList<Produit> productsToAdd = (ArrayList<Produit>) intent.getSerializableExtra("basket");
                if (productsToAdd.size() > 0) {
                    this.updateBasket(productsToAdd);
                }
            } else if (requestCode == MAIN_CATALOGUE) {
                // ici, rien à faire si on revient du mode catalogue
            }
        } // on ne fait rien en cas d'annulation
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
        Intent intent = new Intent(CategoriesActivity.this, MainActivity.class);
        intent.putExtra("id_categ", this.listCategories.get(index).getId());

        this.modeSelected = this.catalogRadioButton.isChecked() ? MAIN_CATALOGUE : MAIN_VENTE;

        intent.putExtra("requestCode", this.modeSelected);
        startActivityForResult(intent, this.modeSelected);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateBasket(ArrayList<Produit> productsToAdd)  {
        productsToAdd.forEach(product -> {
            this.basket.add(product);
            this.basketAmount += product.getPrice();
        });

        this.totalTextView.setText("Total du panier: " + this.basketAmount + "€");
    }
}