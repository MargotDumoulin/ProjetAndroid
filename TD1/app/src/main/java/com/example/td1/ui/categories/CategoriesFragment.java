package com.example.td1.ui.categories;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.td1.ActivityLogin;
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

    private ListView lvCategories;

    private Panier basket;

    private ArrayList<Categorie> listCategories;
    private ArrayList listImgCategories;

    private FloatingActionButton basketValidationActionButton;

    private CategoriesAdapter categoriesAdapter;

    private View root;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_categories, container, false);

        if (savedInstanceState != null) {
            this.basket = ((ActiviteECommerce) this.getActivity()).getBasket();
        } else {
            this.basket = ((ActiviteECommerce) this.getActivity()).getBasket();
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
            this.getViewByPosition(idx, this.lvCategories).findViewById(R.id.categoryProgressBar).setVisibility(View.GONE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onStart() {
        super.onStart();
        String categories = this.getActivity().getIntent().getStringExtra("categories");
        if (categories.length() > 0) {
            try {
                this.basketValidationActionButton = this.root.findViewById(R.id.basketValidationfloatingActionButton);
                this.basketValidationActionButton.setOnClickListener(this::goToBasketValidation);

                if ( ((ActivityLogin) this.getActivity()).isLoggedIn() ) {
                    this.basketValidationActionButton.setVisibility(View.VISIBLE);
                } else {
                    this.basketValidationActionButton.setVisibility(View.INVISIBLE);
                }

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
    }

    public void goToBasketValidation(View v) {
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_gestion_panier);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.basket != null) {
            if (this.basket.getBasketSize() > 0 && !this.basket.getBasketContent().isEmpty()) {
                outState.putSerializable("basket", this.basket);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id_categ", this.listCategories.get(index).getId());
        Navigation.findNavController(view).navigate(R.id.action_nav_boutique_to_venteCatalogueFragment, bundle);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateBasket(Panier productsToAdd, double basketAmountFromMainActivity) {
        if (this.basket == null) {
            this.basket = new Panier(new ArrayList<>());
        }

        productsToAdd.getBasketContent().forEach(product -> {
            this.basket.addArticle(product.first, product.second, product.third);
            ((ActiviteECommerce) this.getActivity()).updateBasket(this.basket);
        });
    }

    public void fillImgCategories() {
        this.listImgCategories = new ArrayList<>();
        for (int i = 0; i < this.listCategories.size(); i++) {
            this.listImgCategories.add(null);
            ImageFromURL loader = new ImageFromURL(this, getContext());
            loader.execute("https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/" + this.listCategories.get(i).getImgSrc(), String.valueOf(i));
        }
    }

    public void setCategoriesAdapter() {
        this.lvCategories = this.root.findViewById(R.id.categoriesListView);
        this.lvCategories.setOnItemClickListener(this);

        this.categoriesAdapter = new CategoriesAdapter(this.getContext(), this.listCategories, this.listImgCategories);
        this.lvCategories.setAdapter(this.categoriesAdapter);
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
}