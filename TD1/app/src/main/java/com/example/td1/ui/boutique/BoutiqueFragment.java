package com.example.td1.ui.boutique;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.td1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BoutiqueFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_boutique, container, false);
        BottomNavigationView bnv = root.findViewById(R.id.bnv_boutique);
        NavHostFragment navHostFragment =
                (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment_boutique);
        NavigationUI.setupWithNavController(bnv, navHostFragment.getNavController());

        return root;
    }
}
