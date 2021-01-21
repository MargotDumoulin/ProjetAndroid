package com.example.td1;

import android.os.Build;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.td1.modele.Client;
import com.example.td1.modele.Panier;
import com.example.td1.modele.Produit;
import com.example.td1.modele.Taille;
import com.example.td1.utils.Triplet;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ActiviteECommerce, ActivityLogin {

    private AppBarConfiguration mAppBarConfiguration;
    private Client loggedInCustomer;
    private Panier basket = new Panier(new ArrayList<Triplet<Produit, Taille, Integer>>());
    private boolean isLoggedIn = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_boutique, R.id.nav_map, R.id.nav_register)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if (savedInstanceState != null) {
            this.basket = (Panier) savedInstanceState.getSerializable("basket");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavDestination currentDestination = navController.getCurrentDestination();

        int itemId = item.getItemId();

        if (itemId == R.id.menu_gestion_panier) {
            if (currentDestination != null && currentDestination.getId() != R.id.menu_gestion_panier) {
                navController.navigate(R.id.menu_gestion_panier);
            }

            return true;
        } else if (itemId == R.id.nav_register) {
            if (currentDestination != null && currentDestination.getId() != R.id.nav_register) {
                if (this.isLoggedIn) {
                    // needs to be changed
                    navController.navigate(R.id.nav_register);
                } else {
                    navController.navigate(R.id.nav_login);
                }
            }

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
//        Fragment currentChildFragment = getChildFragment();
//        if (item.toString().equals(getString(R.string.my_account)) && currentChildFragment != null) {
//            Object currentTag = currentChildFragment.getView().getTag();
//
//           if (currentTag == null || !currentTag.toString().equals(getString(R.string.register_tag))) {
//                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_register);
//            }
//
//            return false;
//        } else if (item.toString().equals(getString(R.string.my_basket)) && currentChildFragment != null) {
//            Object currentTag = currentChildFragment.getView().getTag();
//
//            if (currentTag == null || !currentTag.toString().equals(getString(R.string.my_basket_tag))) {
//                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.menu_gestion_panier);
//            }
//
//            return false;
//        } else {
//            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//            return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
//        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("basket", this.basket);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }

    @Override
    public Panier getBasket() {
        return basket;
    }

    @Override
    public void login() { this.isLoggedIn = true; }

    @Override
    public void updateLoggedInCustomer(Client customer) {
        this.loggedInCustomer = customer;
    }

    @Override
    public Client getLoggedInCustomer() {
        return this.loggedInCustomer;
    }

    @Override
    public void logout() {
        this.isLoggedIn = false;
    }

    @Override
    public void updateBasket(Panier basket) {
        this.basket = basket;
    }

    public Fragment getChildFragment() {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        return navHostFragment == null ? null : navHostFragment.getChildFragmentManager().getFragments().get(0);
    }
}