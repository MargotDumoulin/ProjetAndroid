package com.example.td1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
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
    private int indexToEditOrDelete;
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
        editImageButton.setTag(this.index + "a");
        editImageButton.setOnClickListener(this::onClick);

        ImageButton deleteImageButton = convertView.findViewById(R.id.deleteImageButton);
        deleteImageButton.setTag(this.index + "b");
        deleteImageButton.setOnClickListener(this::onClick);

        return convertView;
    }

    public void handleEditClick() {
        this.isInEditDialog = true;
        this.createEditDialog();
    }

    public void handleDeleteClick() {
        this.isInEditDialog = false;
        this.createDeleteDialog();
    }

    public void onClick(View v) {
        String tag = (String) v.getTag();
        String lastCharacter = tag.substring(tag.length() - 1);
        String tagWithoutLastCharacter = tag.substring(0, tag.length() - 1);
        int tagNumber = Integer.parseInt(tagWithoutLastCharacter);

        this.indexToEditOrDelete = tagNumber;
        if (lastCharacter.equals("a")) {
            this.handleEditClick();
        } else {
            this.handleDeleteClick();
        }
    }

    public void editQuantity(String quantityGiven) {
        if (!quantityGiven.equals("")) {

            try {

                int quantity = Integer.parseInt(quantityGiven);

                if (quantity <= 0) {
                    Toast.makeText(this.getContext(), this.getContext().getString(R.string.must_enter_valid_quantity), Toast.LENGTH_SHORT).show();
                } else {
                    this.basket.get(this.indexToEditOrDelete).third = quantity;
                }

            } catch (Exception e) {
                Toast.makeText(this.getContext(), this.getContext().getString(R.string.must_enter_valid_quantity), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this.getContext(), this.getContext().getString(R.string.must_enter_valid_quantity), Toast.LENGTH_SHORT).show();
        }

        this.responder.changeBasketTotal();
        this.notifyDataSetChanged();
    }

    public void deleteItemFromBasket() {
        this.basket.remove(this.indexToEditOrDelete);
        this.notifyDataSetChanged();
        this.responder.changeBasketTotal();
    }

    public void createEditDialog() {
        QuantityDialog quantityDialog = new QuantityDialog();
        quantityDialog.setUpDialogCaller("MonPanierFragment");
        quantityDialog.show(this.fm, this.getContext().getString(R.string.quantity));
    }

    public void createDeleteDialog() {
        DeleteItemDialog deleteItemDialog = new DeleteItemDialog();
        deleteItemDialog.show(this.fm, this.getContext().getString(R.string.delete_article_from_basket));
    }
}
