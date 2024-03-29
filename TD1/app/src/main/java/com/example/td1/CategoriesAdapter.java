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

import java.util.ArrayList;

public class CategoriesAdapter extends ArrayAdapter<Categorie> {

    private ArrayList<Categorie> listCategories;
    private ArrayList<Bitmap> listImgCategories;

    public CategoriesAdapter(Context context, ArrayList<Categorie> list, ArrayList<Bitmap> listImg) {
        super(context, 0, list);
        this.listCategories = list;
        this.listImgCategories = listImg;
    }

    public View getView(int index, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_categories, parent, false);
        }

        TextView tv = convertView.findViewById(R.id.titleIlcTextView);
        tv.setText(this.listCategories.get(index).getTitle());

        ImageView img = convertView.findViewById(R.id.visualIlcImageView);
        img.setImageBitmap(this.listImgCategories.get(index));

        return convertView;
    }
}
