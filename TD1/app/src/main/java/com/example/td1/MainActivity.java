package com.example.td1;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.td1.modele.Client;
import com.example.td1.modele.Panier;
import com.example.td1.modele.Produit;
import com.example.td1.modele.Taille;
import com.example.td1.ui.monPanier.MonPanierFragment;
import com.example.td1.ui.venteCatalogue.VenteCatalogueFragment;
import com.example.td1.utils.Triplet;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.RequiresApi;
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

public class MainActivity extends AppCompatActivity implements ActivityECommerce, ActivityLogin {

    private AppBarConfiguration mAppBarConfiguration;
    private Client loggedInCustomer;
    private Panier basket = new Panier(new ArrayList<Triplet<Produit, Taille, Integer>>());
    private boolean isLoggedIn = false;

    private Menu menu;

    private TextView accountNameTextView;
    private TextView accountIdentifierTextView;

    private NavigationView navigationView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        this.navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_boutique, R.id.nav_map, R.id.nav_mention_legales)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(this.navigationView, navController);

        if (savedInstanceState != null) {
            this.basket = (Panier) savedInstanceState.getSerializable("basket");
            this.loggedInCustomer = (Client) savedInstanceState.getSerializable("customer");
            this.isLoggedIn = savedInstanceState.getBoolean("isLoggedIn");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        View headerView = this.navigationView.getHeaderView(0);
        this.accountNameTextView = (TextView) headerView.findViewById(R.id.accountNameTextView);
        this.accountIdentifierTextView = (TextView) headerView.findViewById(R.id.accountIdentifierTextView);

        if (this.isLoggedIn) {
            this.updateDrawerWithCustomerInfo(this.loggedInCustomer);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavDestination currentDestination = navController.getCurrentDestination();

        int itemId = item.getItemId();

        if (itemId == R.id.nav_gestion_panier) {
            if (currentDestination != null && currentDestination.getId() != R.id.nav_gestion_panier) {
                navController.navigate(R.id.nav_gestion_panier);
            }

            return true;
        } else if (itemId == R.id.nav_logout) {
            this.logout();
            // clears nav history
            navController.popBackStack(R.id.nav_boutique, true);
            navController.navigate(R.id.nav_boutique);

            return true;

        } else if (itemId == R.id.nav_my_account) {
            if (currentDestination != null && currentDestination.getId() != R.id.nav_my_account) {
                if (this.isLoggedIn) {
                    if (currentDestination.getId() != R.id.nav_edit_personal_info) {
                        navController.navigate(R.id.nav_my_account);
                    }
                } else {
                    if (currentDestination.getId() != R.id.nav_login) {
                        navController.navigate(R.id.nav_login);
                    }
                }
            }

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("basket", this.basket);
        outState.putSerializable("customer", this.loggedInCustomer);
        outState.putBoolean("isLoggedIn", this.isLoggedIn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        this.changeMenu(menu);
        return true;
    }

    public void changeMenu(Menu menu) {
        if (this.isLoggedIn) {
            menu.getItem(0).setIcon(R.drawable.ic_baseline_account_circle_24);
            menu.getItem(1).setEnabled(true);
            menu.getItem(2).setVisible(true);
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_person_outline_white_24dp);
            menu.getItem(1).setEnabled(false);
            menu.getItem(2).setVisible(false);
        }
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
    public void login() {
        this.isLoggedIn = true;
        this.changeMenu(this.menu);
    }

    @Override
    public void updateLoggedInCustomer(Client customer) {
        this.loggedInCustomer = customer;
    }

    @Override
    public Client getLoggedInCustomer() {
        return this.loggedInCustomer;
    }

    @Override
    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    @Override
    public void logout() {
        this.basket = new Panier(new ArrayList<Triplet<Produit, Taille, Integer>>());
        this.isLoggedIn = false;
        this.changeMenu(this.menu);
        this.removeInfoFromDrawer();
        this.loggedInCustomer = null;
    }

    @Override
    public void updateBasket(Panier basket) {
        this.basket = basket;
    }

    @Override
    public void updateDrawerWithCustomerInfo(Client customer) {
        this.accountIdentifierTextView.setText(customer.getIdentifier());
        String params[] = new String[2];
        params[0] = customer.getLastname();
        params[1] = customer.getFirstname();
        this.accountNameTextView.setText(String.format(getString(R.string.fullname), params[1], params[0]));
    }

    @Override
    public void removeInfoFromDrawer() {
        this.accountIdentifierTextView.setText(getString(R.string.nav_header_subtitle));
        this.accountNameTextView.setText(getString(R.string.nav_header_title));
    }

    public void onUserSelectValue(String selectedValue, String dialogCaller) {
        Fragment navHost = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        switch (dialogCaller) {
            case "VenteCatalogueFragment": {
                Fragment boutique = navHost.getChildFragmentManager().getFragments().get(0);
                Fragment navHostBoutique = boutique.getChildFragmentManager().getFragments().get(0);
                VenteCatalogueFragment venteCatalogue = (VenteCatalogueFragment) navHostBoutique.getChildFragmentManager().getFragments().get(0);
                venteCatalogue.onQuantityGiven(selectedValue);
                break;
            }
            case "MonPanierFragment": {
                MonPanierFragment monPanier = (MonPanierFragment) navHost.getChildFragmentManager().getFragments().get(0);
                monPanier.onQuantityGiven(selectedValue);
                break;
            }
        }

    }

    public void onUserDeleteItemFromBasket() {
        Fragment navHost = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        MonPanierFragment monPanier = (MonPanierFragment) navHost.getChildFragmentManager().getFragments().get(0);
        monPanier.onDeleteItemFromBasket();
    }
}