package com.example.td1.ui.favoris;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.td1.ActivityECommerce;
import com.example.td1.ActivityLogin;
import com.example.td1.DAO.ProductDAO;
import com.example.td1.ImageFromURL;
import com.example.td1.R;
import com.example.td1.WaitingData;
import com.example.td1.modele.Produit;
import com.example.td1.modele.Taille;
import com.example.td1.ui.venteCatalogue.VenteCatalogueFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavorisFragment extends VenteCatalogueFragment implements WaitingData {

    private boolean noFavoritesFound;
    private boolean noFavoritesAnymore;

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

            this.noFavoritesFound = savedInstanceState.getBoolean("noFavoritesFound");
            this.noFavoritesAnymore = savedInstanceState.getBoolean("noFavoritesAnymore");

            if (!savedInstanceState.getBoolean("noProducts") && !this.noFavoritesFound && !this.noFavoritesAnymore) {
                this.listProduitToShow = (ArrayList<Produit>) savedInstanceState.getSerializable("listProduitToShow");
                this.loadImages();

                this.basket = ((ActivityECommerce) this.getActivity()).getBasket();

                this.index = savedInstanceState.getInt("index");
                this.isImageZoomed = savedInstanceState.getBoolean("isImageZoomed");
                this.alreadyHaveInfo = true;
            }

        } else {
            this.listProduitToShow = new ArrayList<Produit>();
            this.isImageZoomed = false;
            this.index = 0;

            this.basket = ((ActivityECommerce) this.getActivity()).getBasket();

            ProductDAO.findAllStarred(this, ((ActivityLogin) this.getActivity()).getLoggedInCustomer().getId());
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (this.noFavoritesFound || this.noFavoritesAnymore) {
            this.progressBar.setVisibility(View.INVISIBLE);
            this.progressBarView.setVisibility(View.INVISIBLE);
            this.showNoFavoritesFound();
        }
     }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("noFavoritesAnymore", this.noFavoritesAnymore); // removed all favorites when already launched :)
        outState.putBoolean("noFavoritesFound", this.noFavoritesFound); // no favorites found in db at launch

    }

    public void onClickBtnHeartFilled(View v) {
        this.filledHeartImageButton.setVisibility(View.INVISIBLE);
        this.outlinedHeartImageButton.setVisibility(View.VISIBLE);

        ProductDAO.unstarProduct(this, this.listProduitToShow.get(this.index).getId(), ((ActivityLogin) this.getActivity()).getLoggedInCustomer().getId());

        this.listProduitToShow.get(this.index).setFavori(false);
        this.listProduitToShow.remove(this.index);
        this.listImgProduitToShow.remove(this.index);

        Toast.makeText(this.getContext(), getString(R.string.remove_from_favorites), Toast.LENGTH_LONG).show();

        if (this.listProduitToShow.isEmpty()) {

            this.whiteBlankView.setVisibility(View.VISIBLE);
            this.noProductsTextView.setVisibility(View.VISIBLE);
            this.pullImageView.setVisibility(View.INVISIBLE);
            this.outlinedHeartImageButton.setVisibility(View.INVISIBLE);
            this.sizeSpinner.setVisibility(View.INVISIBLE);
            this.basketImageButton.setVisibility(View.INVISIBLE);
            this.noFavoritesAnymore = true;

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

    public void showNoFavoritesFound() {
        this.pullImageView.setVisibility(View.INVISIBLE);
        this.whiteBlankView.setVisibility(View.VISIBLE);
        this.noProductsTextView.setVisibility(View.VISIBLE);
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
                        this.hideProgressBar();
                    }

                } else {
                    Produit product = new Produit(o.getInt("id_produit"), o.getInt("id_categorie"), o.getDouble("tarif"), o.getString("visuel"), o.getString("description"), o.getString("titre"), o.getBoolean("favori"), new ArrayList<>());
                    this.listProduitToShow.add(product);
                    this.showPullInfo(this.index);
                    this.enablePrevNextButtons(this.index);

                    if (i == response.length() - 1) {
                        for (int y = 0; y < this.listProduitToShow.size(); y++) {
                            this.listImgProduitToShow.add(null);
                            ImageFromURL loader = new ImageFromURL(this, getContext());
                            loader.execute("https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/" + this.listProduitToShow.get(y).getImgSrc(), String.valueOf(y));
                        }

                        ProductDAO.findAllSizesByStarred(this, ((ActivityLogin) this.getActivity()).getLoggedInCustomer().getId());
                    }
                }
            }

            if (this.listImgProduitToShow.size() <= 0) {

                this.noFavoritesFound = true;
                this.showNoFavoritesFound();
                this.hideProgressBar();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
