package com.example.td1;

import android.content.Context;
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

    public CategoriesAdapter(Context context, ArrayList<Categorie> list) {
        super(context, 0, list);
        this.listCategories = list;
    }

    public View getView(int index, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_categories, parent, false);

            TextView tv = convertView.findViewById(R.id.ilc_title);
            tv.setText(this.listCategories.get(index).getTitle());

            ImageView img = convertView.findViewById(R.id.ilc_visual);
            int id = getContext().getResources().getIdentifier(this.listCategories.get(index).getImgSrc(), "drawable", getContext().getPackageName());

            img.setImageResource(id);

        }
        return convertView;
    }
}
