package com.example.td1;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.td1.modele.Categorie;
import com.example.td1.modele.Order;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order> {

    private ArrayList<Order> listOrders;

    public OrderAdapter(Context context, ArrayList<Order> list) {
        super(context, 0, list);
        this.listOrders = list;
    }

    public View getView(int index, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_categories, parent, false);
        }

        TextView tvTitle = convertView.findViewById(R.id.productTitleIlpTextView);
        tvTitle.setText(this.listOrders.get(index).getProductDescription());

        TextView tvPrice = convertView.findViewById(R.id.productPriceIlpTextView);
        tvPrice.setText(String.valueOf(this.listOrders.get(index).getSize()));

        TextView tvSize = convertView.findViewById(R.id.productSizeIlpTextView);
        tvSize.setText(String.valueOf(this.listOrders.get(index).getQuantity()));

        TextView tvQuantity = convertView.findViewById(R.id.productQuantityIlpTextView);
        tvQuantity.setText(String.valueOf(this.listOrders.get(index).getPrice()));

        return convertView;
    }

}
