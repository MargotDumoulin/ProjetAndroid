package com.example.td1.ui.monPanier;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.td1.ActiviteECommerce;
import com.example.td1.PanierAdapter;
import com.example.td1.R;
import com.example.td1.BasketTotalInterface;
import com.example.td1.modele.Panier;

public class MonPanierFragment extends Fragment implements AdapterView.OnItemClickListener, BasketTotalInterface {

    private View root;

    private ListView lvPanier;
    private PanierAdapter panierAdapter;
    private TextView basketTotalTextView;

    private Panier basket;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_mon_panier, container, false);

        this.basket = ((ActiviteECommerce) this.getActivity()).getBasket();
        this.basketTotalTextView = root.findViewById(R.id.totalTextView);
        this.basketTotalTextView.setText(String.format(getString(R.string.basket_total), this.basket.getBasketTotal()));

        this.setPanierAdapter();
        return root;
    }

    public void setPanierAdapter() {
        this.lvPanier = this.root.findViewById(R.id.panierListView);
        this.lvPanier.setOnItemClickListener(this);

        this.panierAdapter = new PanierAdapter(this.getContext(), this.basket.getBasketContent(), this);
        this.lvPanier.setAdapter(this.panierAdapter);
        this.panierAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // do nothing :)
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void changeBasketTotal() {
        this.basketTotalTextView.setText(String.format(getString(R.string.basket_total), this.basket.getBasketTotal()));
    }
}
