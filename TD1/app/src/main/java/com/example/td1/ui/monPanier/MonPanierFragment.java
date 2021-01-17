package com.example.td1.ui.monPanier;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.td1.R;

public class MonPanierFragment extends Fragment {
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_mon_panier, container, false);
        final TextView textView = root.findViewById(R.id.text_mon_panier);
        textView.setText("Mon Panier !");
        return root;
    }
    public MonPanierFragment(){
        // doesn't do anything special
    }
}
