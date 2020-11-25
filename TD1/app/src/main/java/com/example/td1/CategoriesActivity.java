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
import com.example.td1.modele.Panier;
import com.example.td1.modele.Produit;
import com.example.td1.utils.Triplet;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final int MAIN_VENTE = 0;
    private static final int MAIN_CATALOGUE = 1;

    private ListView lvCategories;
    private RadioButton catalogRadioButton;
    private TextView totalTextView;
    private int modeSelected;
    private Panier basket;
    private ArrayList<Categorie> listCategories;
    private double basketAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        if (savedInstanceState != null) {
            this.basket = (Panier) savedInstanceState.getSerializable("basket");
            this.basketAmount = savedInstanceState.getDouble("basketAmount");
        } else {
            this.basket = new Panier(new ArrayList<Triplet<Integer, String, String>>());
            this.basketAmount = 0;
        }

        this.listCategories = new ArrayList<Categorie>();
        this.listCategories.add(new Categorie(0,"Pull","bear_hoodie"));
        this.listCategories.add(new Categorie(1,"Bonnet","bonnet"));
        this.listCategories.add(new Categorie(2,"Pantalon","pantalon"));
    }

    public void onStart() {
        super.onStart();
        this.lvCategories = this.findViewById(R.id.categoriesListView);
        this.lvCategories.setOnItemClickListener(this);

        CategoriesAdapter adapter = new CategoriesAdapter(this, this.listCategories);
        this.lvCategories.setAdapter(adapter);

        this.catalogRadioButton = this.findViewById(R.id.catalogRadioButton);
        this.totalTextView = this.findViewById(R.id.totalTextView);

        updateTotal();
    }

    public void onClickCreateProduct(View v) {
        Intent intent = new Intent(CategoriesActivity.this, NewProductActivity.class);
        startActivity(intent);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.basket != null) {
            if (this.basket.getBasketSize() > 0 && !this.basket.getBasketContent().isEmpty()) {
                outState.putDouble("basketAmount", this.basketAmount);
                outState.putSerializable("basket", this.basket);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == MainActivity.RETOUR) {
            if (requestCode == MAIN_VENTE) {
                Panier productsToAdd = (Panier) intent.getSerializableExtra("basket");
                double basketAmountFromMainActivity = intent.getDoubleExtra("basketAmount", -1);

                Log.d("null", String.valueOf(!productsToAdd.getBasketContent().isEmpty()));
                if (!productsToAdd.getBasketContent().isEmpty()  && basketAmountFromMainActivity != - 1) {
                    Log.d("null", String.valueOf(productsToAdd.getArticle(0).second));
                    this.updateBasket(productsToAdd, basketAmountFromMainActivity);
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
    public void updateBasket(Panier productsToAdd, double basketAmountFromMainActivity)  {
        if (this.basket == null) { this.basket = new Panier(new ArrayList<>()); }

        productsToAdd.getBasketContent().forEach(product -> {
                this.basket.addArticle(product.first, product.second, product.third);
            // As we don't have a database yet, we can't retrieve the product price with the product's id and add it to basketAmount.
            // Currently, we have to pass a variable with basketAmount from MainActivity to CategoriesActivity in order to get the amount to add.
        });
        this.basketAmount += basketAmountFromMainActivity;
        updateTotal();
    }

    public void updateTotal() {
        this.totalTextView.setText(String.format(getString(R.string.basket_total), this.basketAmount));
    }
}