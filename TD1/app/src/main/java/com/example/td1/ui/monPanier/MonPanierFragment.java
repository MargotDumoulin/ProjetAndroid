package com.example.td1.ui.monPanier;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.example.td1.ActivityECommerce;
import com.example.td1.ActivityLogin;
import com.example.td1.DAO.OrderDAO;
import com.example.td1.PanierAdapter;
import com.example.td1.R;
import com.example.td1.BasketTotalInterface;
import com.example.td1.modele.LigneCommande;
import com.example.td1.modele.Panier;
import com.example.td1.modele.Produit;
import com.example.td1.modele.Taille;
import com.example.td1.utils.Triplet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MonPanierFragment extends Fragment implements AdapterView.OnItemClickListener, BasketTotalInterface, com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {

    private View root;

    private ListView lvPanier;

    private PanierAdapter panierAdapter;

    private TextView basketTotalTextView;

    private FloatingActionButton confirmOrderFloatingActionButton;

    private Panier basket;

    private int orderId = -1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_mon_panier, container, false);

        this.basket = ((ActivityECommerce) this.getActivity()).getBasket();
        this.basketTotalTextView = root.findViewById(R.id.totalTextView);
        this.basketTotalTextView.setText(String.format(getString(R.string.basket_total), this.basket.getBasketTotal()));

        this.confirmOrderFloatingActionButton = this.root.findViewById(R.id.confirmOrderfloatingActionButton);
        this.confirmOrderFloatingActionButton.setOnClickListener(this::onClickCreateOrder);

        this.setPanierAdapter();
        this.changeFloatingButtonVisibility();

        return root;
    }

    public void setPanierAdapter() {
        this.lvPanier = this.root.findViewById(R.id.panierListView);
        this.lvPanier.setOnItemClickListener(this);

        this.panierAdapter = new PanierAdapter(this.getContext(), this.basket.getBasketContent(), this);
        this.lvPanier.setAdapter(this.panierAdapter);
        this.panierAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void changeFloatingButtonVisibility() {
        if (this.basket.getBasketTotal() == 0) {
            this.confirmOrderFloatingActionButton.setVisibility(View.INVISIBLE);
        } else {
            this.confirmOrderFloatingActionButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // do nothing :)
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void changeBasketTotal() {
        this.basketTotalTextView.setText(String.format(getString(R.string.basket_total), this.basket.getBasketTotal()));
        this.changeFloatingButtonVisibility();
    }

    public void onClickCreateOrder(View v) {
        OrderDAO.registerOrder(this, ((ActivityLogin) this.getActivity()).getLoggedInCustomer().getId());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Volley error", error.getMessage());
        Toast.makeText(this.getContext(), R.string.error_db, Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResponse(JSONObject response) {
        try {
            if (this.orderId == -1) {
                    this.orderId = response.getInt("orderId");
                    this.triggerRegisterOrderLine();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void triggerRegisterOrderLine() {
        ArrayList<Triplet<Produit, Taille, Integer>> basketContent = this.basket.getBasketContent();
        for (int y = 0; y < basketContent.size(); y++) {
            LigneCommande orderLine = new LigneCommande(
                    this.orderId,
                    basketContent.get(y).first.getId(),
                    basketContent.get(y).second.getId(),
                    basketContent.get(y).third,
                    basketContent.get(y).first.getPrice()
            );

            try {
                JSONObject orderLineJson = new JSONObject(orderLine.toJson());
                OrderDAO.registerOrderLine(this, orderLineJson);
            } catch (Exception e) {
                Log.e("Error", String.valueOf(e));
            }
        }
        Toast.makeText(this.getContext(), getString(R.string.order_well_registered), Toast.LENGTH_SHORT).show();
        this.basket.removeAllArticles();
        this.changeBasketTotal();
        this.panierAdapter.notifyDataSetChanged();
    }

    public void onQuantityGiven(String quantity) {
        this.panierAdapter.editQuantity(quantity);
    }
}
