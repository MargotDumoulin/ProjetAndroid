package com.example.td1.ui.myAccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.td1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyAccountFragment extends Fragment {

    protected BottomNavigationView bnv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_account, container, false);
        this.bnv = root.findViewById(R.id.bnv_my_account);
        NavHostFragment navHostFragment =
                (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment_my_account);
        NavigationUI.setupWithNavController(this.bnv, navHostFragment.getNavController());

        navHostFragment.getNavController().addOnDestinationChangedListener(this::onNavigationChanged);
        return root;
    }

    private void onNavigationChanged(NavController navController, NavDestination navDestination, Bundle bundle) {
        for (int i = 0; i < this.bnv.getMenu().size(); i++) {
            this.bnv.getMenu().getItem(i).setEnabled(true);
        }

        MenuItem menuItem = this.bnv.getMenu().findItem(navDestination.getId());
        menuItem.setEnabled(false);
    }

}
