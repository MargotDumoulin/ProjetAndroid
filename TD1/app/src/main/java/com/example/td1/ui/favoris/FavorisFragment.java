package com.example.td1.ui.favoris;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.td1.ActiviteECommerce;
import com.example.td1.DAO.ProductDAO;
import com.example.td1.ImageFromURL;
import com.example.td1.R;
import com.example.td1.modele.Produit;
import com.example.td1.modele.Taille;
import com.example.td1.ui.venteCatalogue.VenteCatalogueFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavorisFragment extends VenteCatalogueFragment {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.vente_catalogue_fragment, container, false);

        this.listProduitToShow = new ArrayList<Produit>();
        this.listImgProduitToShow = new ArrayList<Bitmap>();
        this.listSizesLabels = new ArrayList<String>();
        this.alreadyHaveInfo = false;

        if (this.getActivity().getIntent().getSerializableExtra("newProduct") != null) {
            Produit productToAdd = (Produit) this.getActivity().getIntent().getSerializableExtra("newProduct");
            this.listProduitToShow.add(productToAdd);
        }

        if (savedInstanceState != null) {
            this.listProduitToShow = (ArrayList<Produit>) savedInstanceState.getSerializable("listProduitToShow");
            this.listImgProduitToShow = (ArrayList<Bitmap>) savedInstanceState.getSerializable("listImgProduitToShow");

            this.basket = ((ActiviteECommerce) this.getActivity()).getBasket();

            this.index = savedInstanceState.getInt("index");
            this.isImageZoomed = savedInstanceState.getBoolean("isImageZoomed");
            this.productTableLength = savedInstanceState.getInt("productTableLength");
            this.alreadyHaveInfo = true;

        } else {
            this.listProduitToShow = new ArrayList<Produit>();
            this.isImageZoomed = false;
            this.index = 0;

            this.basket = ((ActiviteECommerce) this.getActivity()).getBasket();

            ProductDAO.findAllStarred(this, 1);
        }
        return root;
    }

    public void onClickBtnHeartFilled(View v) {
        this.filledHeartImageButton.setVisibility(View.INVISIBLE);
        this.outlinedHeartImageButton.setVisibility(View.VISIBLE);

        ProductDAO.unstarProduct(this, this.listProduitToShow.get(this.index).getId(), 1);
        this.listProduitToShow.get(this.index).setFavori(false);
        this.listProduitToShow.remove(this.index);
        this.listImgProduitToShow.remove(this.index);

        if (this.listProduitToShow.isEmpty()) {
            this.whiteBlankView.setVisibility(View.VISIBLE);
            this.noProductsTextView.setVisibility(View.VISIBLE);
        } else {
            if (this.listProduitToShow.size() == 1 || this.index == 0) {
                this.index = 0;
            } else {
                this.index = this.index - 1;
            }

            this.showPullInfo(this.index);
            this.enablePrevNextButtons(this.index);
            this.changeImageView(this.index);
        }


    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject o = response.getJSONObject(i);
                // test if it is an array of products or an array of sizes
                if (o.has("libelle")) { // Array of size
                    // Adding sizes for each product :)
                    int indexOfProductToChange = this.getIndexById(o.getInt("id_produit"));
                    this.listProduitToShow.get(indexOfProductToChange).addSize(new Taille(o.getInt("id_taille"), o.getString("libelle")));

                    if (i == response.length() - 1) {
                        this.showPullInfo(this.index);
                    }

                } else {
                    Produit product = new Produit(o.getInt("id_produit"), o.getInt("id_categorie"), o.getDouble("tarif"), o.getString("visuel"), o.getString("description"), o.getString("titre"), o.getBoolean("favori"), new ArrayList<>());
                    this.listProduitToShow.add(product);
                    this.showPullInfo(this.index);
                    this.enablePrevNextButtons(this.index);

                    if (i == response.length() - 1) {

                        for (int y = 0; y < this.listProduitToShow.size(); y++) {
                            this.listImgProduitToShow.add(null);
                            ImageFromURL loader = new ImageFromURL(this);
                            loader.execute("https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/" + this.listProduitToShow.get(y).getImgSrc(), String.valueOf(y));
                        }

                        ProductDAO.findAllSizesByStarred(this, 1);
                    }
                }
            }

            if (this.listImgProduitToShow.size() <= 0) {
                this.whiteBlankView.setVisibility(View.VISIBLE);
                this.noProductsTextView.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
