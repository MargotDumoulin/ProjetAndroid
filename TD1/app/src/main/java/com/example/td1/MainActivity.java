package com.example.td1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.td1.modele.Bonnet;
import com.example.td1.modele.Categorie;
import com.example.td1.modele.Panier;
import com.example.td1.modele.Produit;
import com.example.td1.modele.Pull;
import com.example.td1.utils.Triplet;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener, AdapterView.OnItemSelectedListener {

    private Button prevBtn;
    private Button nextBtn;
    private TextView priceTextView;
    private TextView descriptionTextView;
    private TextView titleTextView;
    private ImageView pullImageView;
    private ImageView pullImageViewZoomed;
    private boolean isImageZoomed;
    private View whiteBackgroundView;
    private ImageButton basketImageButton;
    private ImageButton cancelImageButton;
    private Spinner colorSpinner;
    private Spinner sizeSpinner;

    private static final int MAIN_VENTE = 0;

    private int index;
    private ArrayList<Produit> listProduitToShow;
    private Panier basket;
    private double basketAmount;
    private int idCateg;

    public static final int RETOUR = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.listProduitToShow = new ArrayList<Produit>();

        if (this.getIntent().getSerializableExtra("newProduct") != null) {
            Produit productToAdd = (Produit) this.getIntent().getSerializableExtra("newProduct");
            this.listProduitToShow.add(productToAdd);
        }

        if (savedInstanceState != null) {
            this.listProduitToShow = (ArrayList<Produit>) savedInstanceState.getSerializable("listProduitToShow");
            this.basket = (Panier) savedInstanceState.getSerializable("basket");
            this.basketAmount = savedInstanceState.getDouble("basketAmount");
            this.index = savedInstanceState.getInt("index");
            this.isImageZoomed = savedInstanceState.getBoolean("isImageZoomed");
        } else {
            // -- INITIALIZE ARRAYLIST --
            this.listProduitToShow.add(new Pull(0, 0,45.15, "jigglypuff", "Waw ça c'est du pull tu peux me croire.", "title1"));
            this.listProduitToShow.add(new Pull(1,0,22.35, "sweatshirt", "description", "title2"));
            this.listProduitToShow.add(new Pull(2,0,33, "bunny_hoodie", "description", "title3"));
            this.listProduitToShow.add(new Pull(3,0,26, "bear_hoodie", "description", "title4"));
            this.listProduitToShow.add(new Pull(4,0,12, "christmas_pullover", "description", "title5"));
            this.listProduitToShow.add(new Bonnet(5,1,112, "bonnet", "description", "UN BONNET"));
            this.isImageZoomed = false;
            this.index = 0;

            this.basket = new Panier(new ArrayList<Triplet<Integer, String, String>>());

            this.idCateg = this.getIntent().getIntExtra("id_categ", -1);
            if (this.idCateg != -1) {
                // filter items that corresponds to id_categ
                this.listProduitToShow = new ArrayList<Produit>(this.listProduitToShow
                        .stream()
                        .filter(element -> element.getIdCategorie() == this.idCateg).collect(Collectors.toList()));

            } else {
                // ???
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // -- BUTTONS --
        this.prevBtn = this.findViewById(R.id.prevButton);
        this.nextBtn = this.findViewById(R.id.nextButton);

        // -- IMAGEBUTTONS --
        this.basketImageButton = this.findViewById(R.id.cartImageButton);
        this.cancelImageButton = this.findViewById(R.id.cancelImageButton);

        // -- TEXTVIEWS --
        this.priceTextView = this.findViewById(R.id.priceTextView);
        this.descriptionTextView = this.findViewById(R.id.descriptionTextView);
        this.titleTextView = this.findViewById(R.id.titleTextView);

        // -- IMAGEVIEWS --
        this.pullImageView = this.findViewById(R.id.productImageView);
        this.pullImageViewZoomed = this.findViewById(R.id.expandedImageView);

        // -- SPINNERS --
        this.sizeSpinner = this.findViewById(R.id.sizeSpinner);
        this.colorSpinner = this.findViewById(R.id.colorSpinner);

        this.sizeSpinner.setOnItemSelectedListener(this);
        this.colorSpinner.setOnItemSelectedListener(this);

        // -- VIEWS --
        this.whiteBackgroundView = this.findViewById(R.id.blankView);

        // -- SET VIEWS ON PULL --
        showPullInfo(this.index);
        enablePrevNextButtons(this.index);

        if (this.basket.getBasketContent().isEmpty() || this.basket.getBasketContent() == null || this.basket.getBasketSize() < 0) { disableCancelBasketButtons(); }

        if (this.isImageZoomed) {
            zoomImage();
        }

        if (this.getIntent().getIntExtra("requestCode", 0) == MAIN_VENTE) {
            this.basketImageButton.setVisibility(View.VISIBLE);
            this.cancelImageButton.setVisibility(View.VISIBLE);
        } else {
            this.basketImageButton.setVisibility(View.INVISIBLE);
            this.cancelImageButton.setVisibility(View.INVISIBLE);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("index", this.index);
        outState.putSerializable("listProduitToShow", this.listProduitToShow);
        outState.putSerializable("basket", this.basket);
        outState.putDouble("basketAmount", this.basketAmount);

        if (this.pullImageViewZoomed.getVisibility() == View.VISIBLE) {
            outState.putBoolean("isImageZoomed", true);
        } else {
            outState.putBoolean("isImageZoomed", false);
        }
    }

    public void showPullInfo(int index) {
        this.priceTextView.setText(String.valueOf(this.listProduitToShow.get(index).getPrice()) + '€');
        this.descriptionTextView.setText(this.listProduitToShow.get(index).getDescription());
        this.titleTextView.setText(this.listProduitToShow.get(index).getTitle());

        changeImageView(index);
        enablePrevNextButtons(index);
    }

    public void showToastAddProductToBasket() {
        switch (this.idCateg) {
            case 0:
                Toast.makeText(this, String.format(getString(R.string.add_pull_basket), this.index), Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, String.format(getString(R.string.add_bonnet_basket), this.index), Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, String.format(getString(R.string.add_article_basket), this.index), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void changeImageView(int index) {
        int id = getResources().getIdentifier(this.listProduitToShow.get(index).getImgSrc(), "drawable", getPackageName());
        this.pullImageView.setImageResource(id);
    }

    public void enablePrevNextButtons(int index) {
        if (index == 0 && index == (this.listProduitToShow.size() - 1)) {
            this.prevBtn.setEnabled(false);
            this.nextBtn.setEnabled(false);
        } else if (index == 0) {
            this.prevBtn.setEnabled(false);
        } else if (index == (this.listProduitToShow.size() - 1)) {
            this.nextBtn.setEnabled(false);
        } else {
            this.prevBtn.setEnabled(true);
            this.nextBtn.setEnabled(true);
        }
    }

    public void zoomImage() {
        this.whiteBackgroundView.setVisibility(View.VISIBLE);
        this.whiteBackgroundView.bringToFront();

        int id = getResources().getIdentifier(this.listProduitToShow.get(this.index).getImgSrc(), "drawable", getPackageName());
        this.pullImageViewZoomed.setImageResource(id);
        this.pullImageViewZoomed.setVisibility(View.VISIBLE);
        this.pullImageViewZoomed.bringToFront();
    }

    public void unzoomImage() {
        this.whiteBackgroundView.setVisibility(View.INVISIBLE);
        this.pullImageViewZoomed.setVisibility(View.INVISIBLE);
    }

    public void disableCancelBasketButtons() {
        this.cancelImageButton.setEnabled(false);
        this.basketImageButton.setEnabled(false);
    }

    // ---- TOOLBAR EVENTS ----
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        this.onClickGoBack(null);
    }


    // ---- ON CLICK EVENTS ----
    public void onClickGoBack (View v) {
        Intent intent = new Intent();
        intent.putExtra("basketAmount", this.basketAmount);
        intent.putExtra("basket", this.basket);
        this.setResult(RETOUR, intent);
        this.finish();
    }

    public void onClickBtnNext(View v) {
        this.index++;
        showPullInfo(this.index);
        disableCancelBasketButtons();
    }

    public void onClickBtnPrev(View v) {
        this.index--;
        showPullInfo(this.index);
        disableCancelBasketButtons();
    }

    public void onClickBtnBasket(View v) {
        this.cancelImageButton.setEnabled(true);
        this.basket.addArticle(this.listProduitToShow.get(this.index).getId(), sizeSpinner.getSelectedItem().toString(), colorSpinner.getSelectedItem().toString());
        this.basketAmount += this.listProduitToShow.get(this.index).getPrice();
        showToastAddProductToBasket();
    }

    public void onClickCancel (View v) {
        if (this.basket.getBasketSize() > 0 && this.basket != null) {
            CancelAlert alert = new CancelAlert();
            alert.show(getSupportFragmentManager(), "Suppression");
        }
    }

    public void onClickImage(View v) {
        zoomImage();
    }

    public void onClickImageZoomed(View v) {
        unzoomImage();
    }

    // ---- DIALOG ONCLICK ----
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == -1) {
            this.basket.removeAllArticles();
            Toast.makeText(this, getString(R.string.clear_basket), Toast.LENGTH_LONG).show();
            this.cancelImageButton.setEnabled(false);
        } else {
            Toast.makeText(this, getString(R.string.cancel_clear_basket), Toast.LENGTH_LONG).show();
        }
    }

    // ---- SPINNER EVENTS ----
    public void checkSpinnerValue () {
        if (!this.colorSpinner.getSelectedItem().toString().equals("Choix du coloris") &&  !this.sizeSpinner.getSelectedItem().toString().equals("Choix de la taille")) {
            this.basketImageButton.setEnabled(true);
        } else {
            this.basketImageButton.setEnabled(false);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.checkSpinnerValue();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.basketImageButton.setEnabled(false);
    }
}