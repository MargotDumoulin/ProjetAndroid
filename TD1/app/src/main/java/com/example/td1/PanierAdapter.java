package com.example.td1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.td1.modele.Produit;
import com.example.td1.utils.Triplet;

import java.util.ArrayList;

import static android.provider.Settings.System.getString;


public class PanierAdapter extends ArrayAdapter<Triplet<Produit, String, Integer>> {

    private ArrayList<Triplet<Produit, String, Integer>> basket;
    private int index;
    private EditText input;

    public PanierAdapter(Context context, ArrayList<Triplet<Produit, String, Integer>> basket) {
        super(context, 0, basket);
        this.basket = basket;
    }

    public View getView(int index, View convertView, ViewGroup parent) {
        Log.e("CONTENU", "test");
        Log.e("CONTENU", this.basket.get(index).second);
        this.index = index;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_panier, parent, false);
        }

        TextView tvTitle = convertView.findViewById(R.id.productTitleIlpTextView);
        tvTitle.setText(this.basket.get(index).first.getTitle());

        TextView tvPrice = convertView.findViewById(R.id.productPriceIlpTextView);
        tvPrice.setText(String.valueOf(this.basket.get(index).first.getPrice()));

        TextView tvQuantity = convertView.findViewById(R.id.productQuantityIlpTextView);
        tvQuantity.setText(String.valueOf(this.basket.get(index).third));

        ImageButton editImageButton = convertView.findViewById(R.id.editImageButton);
        editImageButton.setTag(2);
        editImageButton.setOnClickListener(this::onClick);

        ImageButton deleteImageButton = convertView.findViewById(R.id.deleteImageButton);
        deleteImageButton.setTag(3);
        deleteImageButton.setOnClickListener(this::onClick);

        return convertView;
    }

    public void handleEditClick(int index) {
        this.createEditDialog(index);
        Log.e("CONTENU", "wahou! tu as cliqué sur EDIT avec le produit" + this.basket.get(index).first.getId());
    }

    public void handleDeleteClick(int index) {
        Log.e("CONTENU", "wahou! tu as cliqué sur DELETE avec le produit" + this.basket.get(index).first.getId());
    }

    public void onClick(View v) {
        int tag = (int) v.getTag();
        if (tag == 2) {
            this.handleEditClick(this.index);
        } else {
            this.handleDeleteClick(this.index);
        }

    }

    public void onClick(DialogInterface dialog, int which) {
        this.basket.get(this.index).third = Integer.parseInt(this.input.getText().toString());
    }

    public void createEditDialog(int index) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getContext());

        alertDialog.setTitle(R.string.quantity);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(50, 0, 350, 0);

        this.input = new EditText(getContext());
        this.input.setInputType(InputType.TYPE_CLASS_NUMBER);
        layout.addView(this.input, params);

        alertDialog.setView(layout);

        alertDialog.setPositiveButton(android.R.string.yes, this::onClick);

        alertDialog.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }
}
