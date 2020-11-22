package com.example.td1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.td1.modele.Categorie;
import com.example.td1.modele.Produit;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

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

    private static final int MAIN_VENTE = 0;
    private static final int MAIN_CATALOGUE = 1;

    private int index;
    private ArrayList<Produit> listProduitToShow;
    private ArrayList<Produit> basket;

    // TODO: vérifier cette variable (public ou private ?)
    public static final int RETOUR = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.listProduitToShow = new ArrayList<Produit>();
        this.basket = new ArrayList<Produit>();

        if (savedInstanceState != null) {
            this.listProduitToShow = (ArrayList<Produit>) savedInstanceState.getSerializable("listProduitToShow");
            this.index = savedInstanceState.getInt("index");
            this.isImageZoomed = savedInstanceState.getBoolean("isImageZoomed");
        } else {
            // -- INITIALIZE ARRAYLIST --
            this.listProduitToShow.add(new Produit(0, 0,45, "jigglypuff", "Waw ça c'est du pull tu peux me croire.", "title1"));
            this.listProduitToShow.add(new Produit(1,0,22, "sweatshirt", "description", "title2"));
            this.listProduitToShow.add(new Produit(2,0,33, "bunny_hoodie", "description", "title3"));
            this.listProduitToShow.add(new Produit(3,0,26, "bear_hoodie", "description", "title4"));
            this.listProduitToShow.add(new Produit(4,0,12, "christmas_pullover", "description", "title5"));
            this.listProduitToShow.add(new Produit(5,1,112, "christmas_pullover", "description", "UN BONNET"));
            this.isImageZoomed = false;
            this.index = 0;

            if (this.getIntent().getIntExtra("id_categ", -1) != -1) {
                // filter items that corresponds to id_categ
                this.listProduitToShow = new ArrayList<Produit>(this.listProduitToShow
                        .stream()
                        .filter(element -> element.getIdCategorie() == this.getIntent().getIntExtra("id_categ", -1)).collect(Collectors.toList()));
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

        // -- VIEWS --
        this.whiteBackgroundView = this.findViewById(R.id.blankView);

        // -- SET VIEWS ON PULL --
        showPullInfo(this.index);
        enableButtons(this.index);

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

        if (this.pullImageViewZoomed.getVisibility() == View.VISIBLE) {
            outState.putBoolean("isImageZoomed", true);
        } else {
            outState.putBoolean("isImageZoomed", false);
        }
    }

    public void onClickBtnNext(View v) {
        this.index++;
        showPullInfo(this.index);
    }

    public void onClickBtnPrev(View v) {
        this.index--;
        showPullInfo(this.index);
    }

    public void showPullInfo(int index) {
        this.priceTextView.setText(String.valueOf(this.listProduitToShow.get(index).getPrice()) + '€');
        this.descriptionTextView.setText(this.listProduitToShow.get(index).getDescription());
        this.titleTextView.setText(this.listProduitToShow.get(index).getTitle());
        changeImageView(index);

        enableButtons(index);
    }

    public void changeImageView(int index) {
        int id = getResources().getIdentifier(this.listProduitToShow.get(index).getImgSrc(), "drawable", getPackageName());
        this.pullImageView.setImageResource(id);
    }

    public void enableButtons(int index) {
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

    public void onClickBtnBasket(View v) {
        this.basket.add(this.listProduitToShow.get(this.index));
        Toast.makeText(this, String.format(getString(R.string.ajout_panier), this.index), Toast.LENGTH_SHORT).show();
    }

    public void onClickImage(View v) {
        zoomImage();
    }

    public void onClickImageZoomed(View v) {
        unzoomImage();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        Log.d("MainActivity", "test");
        this.onClickGoBack(null);
    }

    public void onClickGoBack (View v) {
        Intent intent = new Intent();
        intent.putExtra("basket", this.basket);
        this.setResult(RETOUR, intent);
        this.finish();
    }
}