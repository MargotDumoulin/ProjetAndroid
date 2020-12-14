package com.example.td1;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.td1.DAO.ProductDAO;
import com.example.td1.modele.Categorie;
import com.example.td1.modele.Panier;
import com.example.td1.modele.Produit;
import com.example.td1.utils.Triplet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener, AdapterView.OnItemSelectedListener, ActivityWaitingImage, com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {

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
    private int productTableLength;
    private ArrayList <Bitmap> listImgProduitToShow;
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
        this.listImgProduitToShow = new ArrayList<Bitmap>();

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
            this.listProduitToShow = new ArrayList<Produit>();
            this.isImageZoomed = false;
            this.index = 0;

            this.basket = new Panier(new ArrayList<Triplet<Integer, String, String>>());

            this.idCateg = this.getIntent().getIntExtra("id_categ", -1);
            if (this.idCateg != -1) {
                // filter items that corresponds to id_categ
                this.listProduitToShow = new ArrayList<Produit>(this.listProduitToShow
                        .stream()
                        .filter(element -> element.getIdCategorie() == this.idCateg).collect(Collectors.toList()));
                ProductDAO.getProductTableLength(this, this.idCateg);
                ProductDAO.findProduct(this, this.index, this.idCateg);
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

        if (this.basket.getBasketContent().isEmpty() || this.basket.getBasketContent() == null || this.basket.getBasketSize() < 0) {
            this.cancelImageButton.setEnabled(false);
        }

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

    @Override
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
        this.priceTextView.setText(String.format(getString(R.string.price_text_view), this.listProduitToShow.get(index).getPrice()));
        this.descriptionTextView.setText(this.listProduitToShow.get(index).getDescription());
        this.titleTextView.setText(this.listProduitToShow.get(index).getTitle());
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
        this.listImgProduitToShow.add(null);
        ImageFromURL loader = new ImageFromURL(this);
        loader.execute("https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/" + this.listProduitToShow.get(index).getImgSrc(), String.valueOf(index));
    }

    public void enablePrevNextButtons(int index) {
        if (index == 0 && index == (this.productTableLength - 1)) {
            this.prevBtn.setEnabled(false);
            this.nextBtn.setEnabled(false);
        } else if (index == 0 && (this.productTableLength - 1) > 1) {
            this.prevBtn.setEnabled(true);
            this.prevBtn.setEnabled(false);
        } else if (index == (this.productTableLength - 1) && (index != 0)) {
            this.prevBtn.setEnabled(true);
            this.nextBtn.setEnabled(false);
        } else if (index == 0 && (this.productTableLength - 1) != 0) {
            this.prevBtn.setEnabled(false);
            this.nextBtn.setEnabled(true);
        } else {
            this.prevBtn.setEnabled(true);
            this.nextBtn.setEnabled(true);
        }
    }

    public void zoomImage() {
        this.whiteBackgroundView.setVisibility(View.VISIBLE);
        this.whiteBackgroundView.bringToFront();
        
        this.pullImageViewZoomed.setVisibility(View.VISIBLE);
        this.pullImageViewZoomed.bringToFront();
    }

    public void unzoomImage() {
        this.whiteBackgroundView.setVisibility(View.INVISIBLE);
        this.pullImageViewZoomed.setVisibility(View.INVISIBLE);
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
        ProductDAO.findProduct(this, this.index, this.idCateg);
        checkSpinnerValue();
    }

    public void onClickBtnPrev(View v) {
        this.index--;
        ProductDAO.findProduct(this, this.index, this.idCateg);
        checkSpinnerValue();
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
            this.basketAmount = 0;
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
        checkSpinnerValue();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.basketImageButton.setEnabled(false);
    }

    @Override
    public void getImage(Object[] results)  {
        if (results[0] != null) {
            int idx = Integer.parseInt(results[1].toString());
            Bitmap img = (Bitmap) results[0];

            boolean imgNotFound = this.listImgProduitToShow.size() < index;

            if (imgNotFound) {
                int id = getResources().getIdentifier("img", "drawable", getPackageName());
                this.pullImageView.setImageResource(id);
            } else {
                this.listImgProduitToShow.set(idx, img);
                this.pullImageView.setImageBitmap(this.listImgProduitToShow.get(index));

                this.pullImageViewZoomed.setImageBitmap(img);
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("test", error + "là");
        Log.e("test", String.valueOf(this.index));
        Toast.makeText(this, R.string.error_bdd, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONObject o = response;

            if (response.has("COUNT(*)")) {
                this.productTableLength = response.getInt("COUNT(*)");
                this.enablePrevNextButtons(this.index);
            } else {
                Produit product = new Produit(o.getInt("id_produit"), o.getInt("id_categorie"), o.getDouble("tarif"), o.getString("visuel"), o.getString("description"), o.getString("titre"));
                this.listProduitToShow.add(product);
                this.showPullInfo(this.index);
                this.changeImageView(this.index);
                this.enablePrevNextButtons(this.index);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}