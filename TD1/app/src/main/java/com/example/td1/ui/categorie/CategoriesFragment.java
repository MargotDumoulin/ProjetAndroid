package com.example.td1.ui.categorie;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.td1.ActivityWaitingImage;
import com.example.td1.CategoriesAdapter;
import com.example.td1.ImageFromURL;
import com.example.td1.ActiviteECommerce;
import com.example.td1.R;
import com.example.td1.modele.Categorie;
import com.example.td1.modele.Panier;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoriesFragment extends Fragment implements AdapterView.OnItemClickListener, ActivityWaitingImage {

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
    private FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_categories, container, false);

        if (savedInstanceState != null) {
            //this.basket = (Panier) savedInstanceState.getSerializable("basket");
            //this.basketAmount = savedInstanceState.getDouble("basketAmount");

            this.basket = ((ActiviteECommerce) this.getActivity()).getPanier();
            this.basketAmount = ((ActiviteECommerce) this.getActivity()).getPanierPrix();

        } else {
            this.basket = ((ActiviteECommerce) this.getActivity()).getPanier();
            this.basketAmount = ((ActiviteECommerce) this.getActivity()).getPanierPrix();
        }
        return root;
    }

    @Override
    public void getImage(Object[] results) {
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
        this.floatingActionButton = this.root.findViewById(R.id.floatingActionButton);
        this.floatingActionButton.setOnClickListener(this::onClickCreateProduct);

        String categories = this.getActivity().getIntent().getStringExtra("categories");

        if (categories.length() > 0) {
            try {
                this.listCategories = new ArrayList<Categorie>();
                JSONArray catArray = new JSONArray(categories);

                for (int i = 0; i < catArray.length(); i++) {
                    JSONObject o = catArray.getJSONObject(i);
                    Categorie cat = new Categorie(o.getInt("id_categorie"), o.getString("titre"), o.getString("visuel"));
                    this.listCategories.add(cat);
                }
                this.fillImgCategories();
                this.setCategoriesAdapter();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        updateTotal();
    }

    public void onClickCreateProduct(View view) {
        /** 
        Intent intent = new Intent(CategoriesFragment.this.getContext(), NewProductFragment.class);
        startActivity(intent);**/
        Bundle bundle = new Bundle();
        Navigation.findNavController(view).navigate(R.id.action_nav_boutique_to_newProductFragment,bundle);
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
     * @RequiresApi(api = Build.VERSION_CODES.N)
     * @Override protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
     * super.onActivityResult(requestCode, resultCode, intent);
     * if (resultCode == VenteCatalogueActivity.RETOUR) {
     * if (requestCode == MAIN_VENTE) {
     * Panier productsToAdd = (Panier) intent.getSerializableExtra("basket");
     * double basketAmountFromMainActivity = intent.getDoubleExtra("basketAmount", -1);
     * <p>
     * Log.d("null", String.valueOf(!productsToAdd.getBasketContent().isEmpty()));
     * if (!productsToAdd.getBasketContent().isEmpty()  && basketAmountFromMainActivity != - 1) {
     * Log.d("null", String.valueOf(productsToAdd.getArticle(0).second));
     * this.updateBasket(productsToAdd, basketAmountFromMainActivity);
     * }
     * } else if (requestCode == MAIN_CATALOGUE) {
     * // ici, rien à faire si on revient du mode catalogue
     * }
     * } // on ne fait rien en cas d'annulation
     * }
     **/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_categ", this.listCategories.get(index).getId());
        this.modeSelected = this.catalogRadioButton.isChecked() ? MAIN_CATALOGUE : MAIN_VENTE;
        bundle.putInt("requestCode",modeSelected);

        Navigation.findNavController(view).navigate(R.id.action_nav_boutique_to_venteCatalogueFragment, bundle);
        /**
         Intent intent = new Intent(CategoriesFragment.this, VenteCatalogueActivity.class);
         intent.putExtra("id_categ", this.listCategories.get(index).getId());

         this.modeSelected = this.catalogRadioButton.isChecked() ? MAIN_CATALOGUE : MAIN_VENTE;

         intent.putExtra("requestCode", this.modeSelected);
         startActivityForResult(intent, this.modeSelected);**/
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateBasket(Panier productsToAdd, double basketAmountFromMainActivity) {
        if (this.basket == null) {
            this.basket = new Panier(new ArrayList<>());
        }

        productsToAdd.getBasketContent().forEach(product -> {
            this.basket.addArticle(product.first, product.second);
            ((ActiviteECommerce) this.getActivity()).updatePanier(this.basket);
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
        this.lvCategories.setOnItemClickListener(this);

        this.categoriesAdapter = new CategoriesAdapter(this.getContext(), this.listCategories, this.listImgCategories);
        this.lvCategories.setAdapter(this.categoriesAdapter);
    }
}