package com.example.td1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.td1.modele.OrderLine;

import java.util.ArrayList;

public class OrderLinesAdapter extends ArrayAdapter<OrderLine> {

    private ArrayList<OrderLine> orderLines;

    public OrderLinesAdapter(Context context, ArrayList<OrderLine> list) {
        super(context, 0, list);
        this.orderLines = list;
    }

    public View getView(int index, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_order_lines, parent, false);
        }

        TextView tvDescription = convertView.findViewById(R.id.productTitleIlpTextView);
        tvDescription.setText(this.orderLines.get(index).getProductDescription());

        TextView tvPrice = convertView.findViewById(R.id.productPriceIlpTextView);
        tvPrice.setText(String.valueOf(this.orderLines.get(index).getPrice()));

        TextView tvSize = convertView.findViewById(R.id.productSizeIlpTextView);
        tvSize.setText(String.valueOf(this.orderLines.get(index).getSize()));

        TextView tvQuantity = convertView.findViewById(R.id.productQuantityIlpTextView);
        tvQuantity.setText(String.valueOf(this.orderLines.get(index).getQuantity()));

        return convertView;
    }

}
