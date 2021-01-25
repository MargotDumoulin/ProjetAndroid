package com.example.td1.ui.viewLastOrder;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.td1.CategoriesAdapter;
import com.example.td1.OrderAdapter;
import com.example.td1.R;
import com.example.td1.modele.Order;

import java.util.ArrayList;

public class ViewLastOrderFragment extends Fragment {

    private View root;

    private ArrayList<Order> listOrders;

    private ListView lvOrder;

    private ArrayAdapter orderAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_last_order, container, false);

        return root;
    }

    public void setOrderAdapter() {
        this.lvOrder = this.root.findViewById(R.id.ordersListView);
        this.orderAdapter = new OrderAdapter(this.getContext(), this.listOrders);
        this.lvOrder.setAdapter(this.orderAdapter);
    }


}
