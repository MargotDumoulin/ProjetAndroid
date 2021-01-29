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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.td1.modele.Produit;
import com.example.td1.modele.Taille;
import com.example.td1.utils.Triplet;

import java.util.ArrayList;


public class PanierAdapter extends ArrayAdapter<Triplet<Produit, Taille, Integer>> {

    private ArrayList<Triplet<Produit, Taille, Integer>> basket;
    private int index;
    private EditText input;
    private boolean isInEditDialog;
    private BasketTotalInterface responder;
    private FragmentManager fm;

    public PanierAdapter(Context context, ArrayList<Triplet<Produit, Taille, Integer>> basket, BasketTotalInterface responder) {
        super(context, 0, basket);
        this.basket = basket;
        this.responder = responder;
        this.fm = ((MainActivity) context).getSupportFragmentManager();
    }

    public View getView(int index, View convertView, ViewGroup parent) {
        this.index = index;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_panier, parent, false);
        }

        TextView tvTitle = convertView.findViewById(R.id.productTitleIlpTextView);
        tvTitle.setText(this.basket.get(index).first.getTitle());

        TextView tvPrice = convertView.findViewById(R.id.productPriceIlpTextView);
        tvPrice.setText(String.valueOf(this.basket.get(index).first.getPrice()));

        TextView tvSize = convertView.findViewById(R.id.productSizeIlpTextView);
        tvSize.setText(String.valueOf(this.basket.get(index).second.getLabel()));

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
        this.isInEditDialog = true;
        this.createEditDialog(index);
    }

    public void handleDeleteClick(int index) {
        this.isInEditDialog = false;
        this.createDeleteDialog(index);
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
        if (this.isInEditDialog) {

            if (!this.input.equals("")) {
                int quantity = Integer.parseInt(this.input.getText().toString());

                if (quantity <= 0) {
                    Toast.makeText(this.getContext(), this.getContext().getString(R.string.must_enter_valid_quantity), Toast.LENGTH_SHORT).show();
                } else {
                    this.basket.get(this.index).third = Integer.parseInt(this.input.getText().toString());
                }
            } else {
                Toast.makeText(this.getContext(), this.getContext().getString(R.string.must_enter_valid_quantity), Toast.LENGTH_SHORT).show();
            }

        } else {
            this.basket.remove(this.index);
            this.notifyDataSetChanged();
        }
        this.responder.changeBasketTotal();
    }

    public void editQuantity(String quantityGiven) {
        Log.e("QUANTITY", "" + quantityGiven);
        if (!quantityGiven.equals("")) {

            int quantity = Integer.parseInt(quantityGiven);

            if (quantity <= 0) {
                Toast.makeText(this.getContext(), this.getContext().getString(R.string.must_enter_valid_quantity), Toast.LENGTH_SHORT).show();
            } else {
                this.basket.get(this.index).third = quantity;
            }
        } else {
            Toast.makeText(this.getContext(), this.getContext().getString(R.string.must_enter_valid_quantity), Toast.LENGTH_SHORT).show();
        }

        this.responder.changeBasketTotal();
    }

    public void createEditDialog(int index) {
        QuantityDialog quantityDialog = new QuantityDialog();
        quantityDialog.setUpDialogCaller("MonPanierFragment");
        quantityDialog.show(this.fm, this.getContext().getString(R.string.quantity));
    }
    
    public void createDeleteDialog(int index) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                getContext());

        alertDialog.setTitle(R.string.delete_article_from_basket);

        alertDialog.setPositiveButton(android.R.string.yes, this::onClick);

        alertDialog.setNegativeButton(android.R.string.no, (dialog, which) -> dialog.cancel());

        alertDialog.show();
    }
}
