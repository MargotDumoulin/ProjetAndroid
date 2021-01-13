package com.example.td1;

import android.os.Bundle;
import android.view.Menu;

import com.example.td1.modele.Panier;
import com.example.td1.utils.Paired;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActiviteECommerce {

    private AppBarConfiguration mAppBarConfiguration;
    private Panier basket = new Panier(new ArrayList<Paired<Integer, String>>());
    private double basketPrice;

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
                R.id.nav_home, R.id.nav_gallery/**, R.id.nav_slideshow**/, R.id.nav_boutique)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        if (savedInstanceState != null) {
            this.basket = (Panier)savedInstanceState.getSerializable("basket");
            this.basketPrice = savedInstanceState.getDouble("basketPrice");
        }
    }
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("basketPrice", this.basketPrice);
        outState.putSerializable("basket", this.basket);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainn, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public Panier getPanier() {
        return basket;
    }

    @Override
    public void updatePanier(Panier basket) {
        this.basket = basket;
    }

    @Override
    public void updatePanierPrix(double basketPrice) {
        this.basketPrice = basketPrice;
    }

    @Override
    public double getPanierPrix() {
        return this.basketPrice;
    }
}