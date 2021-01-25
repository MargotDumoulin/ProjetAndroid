package com.example.td1;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.td1.modele.Categorie;

import java.lang.reflect.Array;
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

         boolean imgNotFound = this.listImgCategories.size() < index;

        if (imgNotFound) {
            int id = R.drawable.ic_male_clothes;
            img.setImageResource(id);
        } else {
            img.setImageBitmap(this.listImgCategories.get(index));
        }

        return convertView;
    }
}
