package com.example.td1;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.td1.DAO.CategorieDAO;
import com.example.td1.modele.Categorie;
import com.example.td1.modele.Panier;
import com.example.td1.utils.Triplet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CategoriesFragment extends Fragment implements/** AdapterView.OnItemClickListener,**/ ActivityWaitingImage, com.android.volley.Response.Listener<JSONArray>, com.android.volley.Response.ErrorListener {

    private static final int MAIN_VENTE = 0;
    private static final int MAIN_CATALOGUE = 1;

    private ListView lvCategories;
    private RadioButton catalogRadioButton;
    private TextView totalTextView;
    private int modeSelected;
    private Panier basket;
    private ArrayList<Categorie> listCategories;
    private ArrayList listImgCategories;
    private double basketAmount;
    private CategoriesAdapter categoriesAdapter;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.root=inflater.inflate(R.layout.fragment_categories, container, false);
        CategorieDAO.findAll(this);

        if (savedInstanceState != null) {
            this.basket = (Panier) savedInstanceState.getSerializable("basket");
            this.basketAmount = savedInstanceState.getDouble("basketAmount");
        } else {
            this.basket = new Panier(new ArrayList<Triplet<Integer, String, String>>());
            this.basketAmount = 0;
        }
        return root;
    }

    @Override
    public void getImage(Object[] results)  {
        if (results[0] != null) {
            int idx = Integer.parseInt(results[1].toString());
            Bitmap img = (Bitmap) results[0];
            this.listImgCategories.set(idx, img);
            this.categoriesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        this.catalogRadioButton = this.root.findViewById(R.id.catalogRadioButton);
        this.totalTextView = this.root.findViewById(R.id.totalTextView);

        updateTotal();
    }

    public void onClickCreateProduct(View v) {
        Intent intent = new Intent(CategoriesFragment.this.getContext(), NewProductActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.basket != null) {
            if (this.basket.getBasketSize() > 0 && !this.basket.getBasketContent().isEmpty()) {
                outState.putDouble("basketAmount", this.basketAmount);
                outState.putSerializable("basket", this.basket);
            }
        }
    }
/**
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == CatalogueActivity.RETOUR) {
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
        Intent intent = new Intent(CategoriesFragment.this.getContext(), CatalogueActivity.class);
        intent.putExtra("id_categ", this.listCategories.get(index).getId());

        this.modeSelected = this.catalogRadioButton.isChecked() ? MAIN_CATALOGUE : MAIN_VENTE;

        intent.putExtra("requestCode", this.modeSelected);
        startActivityForResult(intent, this.modeSelected);
    }
**/
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

    public void fillImgCategories() {
        this.listImgCategories = new ArrayList<>();
        for (int i = 0; i < this.listCategories.size(); i++) {
            this.listImgCategories.add(null);
            ImageFromURL loader = new ImageFromURL(this);
            loader.execute("https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/" + this.listCategories.get(i).getImgSrc(), String.valueOf(i));
        }
    }

    public void setCategoriesAdapter() {
        this.lvCategories = this.root.findViewById(R.id.categoriesListView);
        //this.lvCategories.setOnItemClickListener(this);

        this.categoriesAdapter = new CategoriesAdapter(this.getContext(), this.listCategories, this.listImgCategories);
        this.lvCategories.setAdapter(this.categoriesAdapter);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "là");
        Toast.makeText(this.getContext(), R.string.error_bdd, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            this.listCategories = new ArrayList<Categorie>();
            for (int i = 0; i < response.length(); i++) {
                JSONObject o = response.getJSONObject(i);
                Categorie cat = new Categorie(o.getInt("id_categorie"), o.getString("titre"), o.getString("visuel"));
                this.listCategories.add(cat);
            }
            this.fillImgCategories();
            this.setCategoriesAdapter();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}