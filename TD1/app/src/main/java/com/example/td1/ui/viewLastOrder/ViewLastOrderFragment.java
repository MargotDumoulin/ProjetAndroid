package com.example.td1.ui.viewLastOrder;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.td1.R;

public class ViewLastOrderFragment extends Fragment {

    private View root;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_last_order, container, false);

        return root;
    }

}
