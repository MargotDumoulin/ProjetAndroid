package com.example.td1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.td1.modele.Produit;
import com.example.td1.utils.Triplet;

import java.util.ArrayList;


public class PanierAdapter extends ArrayAdapter<Triplet<Produit, String, Integer>> {

    private ArrayList<Triplet<Produit, String, Integer>> basket;

    public PanierAdapter(Context context, ArrayList<Triplet<Produit, String, Integer>> basket) {
        super(context, 0, basket);
        this.basket = basket;
    }

    public View getView(int index, View convertView, ViewGroup parent) {
        Log.e("CONTENU", "test");
        Log.e("CONTENU", this.basket.get(index).second);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_panier, parent, false);
        }

        TextView tvTitle = convertView.findViewById(R.id.productTitleIlpTextView);
        tvTitle.setText(this.basket.get(index).first.getTitle());

        TextView tvPrice = convertView.findViewById(R.id.productPriceIlpTextView);
        tvPrice.setText(String.valueOf(this.basket.get(index).first.getPrice()));

        TextView tvQuantity = convertView.findViewById(R.id.productQuantityIlpTextView);
        tvQuantity.setText(String.valueOf(this.basket.get(index).third));

        return convertView;
    }
}
