package com.example.td1;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.android.volley.VolleyError;
import com.example.td1.DAO.ProductDAO;
import com.example.td1.modele.Panier;
import com.example.td1.modele.Produit;
import com.example.td1.utils.Triplet;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CatalogueFragment extends Fragment implements /**DialogInterface.OnClickListener,**/ AdapterView.OnItemSelectedListener, ActivityWaitingImage, com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {

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
    private ArrayList<Bitmap> listImgProduitToShow;
    private Panier basket;
    private double basketAmount;
    private int idCateg;
    private View root;

    public static final int RETOUR = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.root = inflater.inflate(R.layout.fragment_catalogue, container, false);
        /**getSupportActionBar().setDisplayHomeAsUpEnabled(true);**/

        this.listProduitToShow = new ArrayList<Produit>();
        this.listImgProduitToShow = new ArrayList<Bitmap>();

        if (this.getActivity().getIntent().getSerializableExtra("newProduct") != null) {
            Produit productToAdd = (Produit) this.getActivity().getIntent().getSerializableExtra("newProduct");
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
            ProductDAO.findProduct(this, 0);
            this.isImageZoomed = false;
            this.index = 0;

            this.basket = new Panier(new ArrayList<Triplet<Integer, String, String>>());

            this.idCateg = this.getArguments().getInt("id_categ", -1);
            if (this.idCateg != -1) {
                // filter items that corresponds to id_categ
                this.listProduitToShow = new ArrayList<Produit>(this.listProduitToShow
                        .stream()
                        .filter(element -> element.getIdCategorie() == this.idCateg).collect(Collectors.toList()));

            } else {
                // ???
            }
        }
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        // -- BUTTONS --
        this.prevBtn.setOnClickListener(this::onClickBtnPrev);
        this.nextBtn.setOnClickListener(this::onClickBtnNext);

        // -- IMAGEBUTTONS --
        this.basketImageButton = this.root.findViewById(R.id.cartImageButton);
        this.cancelImageButton = this.root.findViewById(R.id.cancelImageButton);

        // -- TEXTVIEWS --
        this.priceTextView = this.root.findViewById(R.id.priceTextView);
        this.descriptionTextView = this.root.findViewById(R.id.descriptionTextView);
        this.titleTextView = this.root.findViewById(R.id.titleTextView);

        // -- IMAGEVIEWS --
        this.pullImageView.setOnClickListener(this::onClickImage);
        this.pullImageViewZoomed.setOnClickListener(this::onClickImageZoomed);

        // -- SPINNERS --
        this.sizeSpinner = this.root.findViewById(R.id.sizeSpinner);
        this.colorSpinner = this.root.findViewById(R.id.colorSpinner);

        this.sizeSpinner.setOnItemSelectedListener(this);
        this.colorSpinner.setOnItemSelectedListener(this);

        // -- VIEWS --
        this.whiteBackgroundView = this.root.findViewById(R.id.blankView);

        if (this.basket.getBasketContent().isEmpty() || this.basket.getBasketContent() == null || this.basket.getBasketSize() < 0) {
            this.cancelImageButton.setEnabled(false);
        }

        if (this.isImageZoomed) {
            zoomImage();
        }

        if (this.getArguments().getInt("requestCode", 0) == MAIN_VENTE) {
            this.basketImageButton.setVisibility(View.VISIBLE);
            this.cancelImageButton.setVisibility(View.VISIBLE);
        } else {
            this.basketImageButton.setVisibility(View.INVISIBLE);
            this.cancelImageButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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

        changeImageView(index);
        enablePrevNextButtons(index);
    }

    public void showToastAddProductToBasket() {
        switch (this.idCateg) {
            case 0:
                Toast.makeText(this.getContext(), String.format(getString(R.string.add_pull_basket), this.index), Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this.getContext(), String.format(getString(R.string.add_bonnet_basket), this.index), Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this.getContext(), String.format(getString(R.string.add_article_basket), this.index), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void changeImageView(int index) {
        this.listImgProduitToShow.add(null);
        ImageFromURL loader = new ImageFromURL(this);
        loader.execute("https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/" + this.listProduitToShow.get(index).getImgSrc() + ".png", String.valueOf(index));
    }

    public void enablePrevNextButtons(int index) {
        if (index == 0 && index == (this.listProduitToShow.size() - 1)) {
            this.prevBtn.setEnabled(false);
            this.nextBtn.setEnabled(false);
        } else if (index == 0 && (this.listProduitToShow.size() - 1) > 1) {
            this.prevBtn.setEnabled(true);
            this.prevBtn.setEnabled(false);
        } else if (index == (this.listProduitToShow.size() - 1) && (index != 0)) {
            this.prevBtn.setEnabled(true);
            this.nextBtn.setEnabled(false);
        } else if (index == 0 && (this.listProduitToShow.size() - 1) != 0) {
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

        int id = getResources().getIdentifier(this.listProduitToShow.get(this.index).getImgSrc(), "drawable", getActivity().getPackageName());
        this.pullImageViewZoomed.setImageResource(id);
        this.pullImageViewZoomed.setVisibility(View.VISIBLE);
        this.pullImageViewZoomed.bringToFront();
    }

    public void unzoomImage() {
        this.whiteBackgroundView.setVisibility(View.INVISIBLE);
        this.pullImageViewZoomed.setVisibility(View.INVISIBLE);
    }

    /**
    // ---- TOOLBAR EVENTS ----
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBackPressed() {
        this.onClickGoBack(null);
    }

    // ---- ON CLICK EVENTS ----
    public void onClickGoBack(View v) {
        Intent intent = new Intent();
        intent.putExtra("basketAmount", this.basketAmount);
        intent.putExtra("basket", this.basket);
        this.setResult(RETOUR, intent);
        this.finish();
    }
    **/
    public void onClickBtnNext(View v) {
        this.index++;
        ProductDAO.findProduct(this, this.index);
        checkSpinnerValue();
    }

    public void onClickBtnPrev(View v) {
        this.index--;
        ProductDAO.findProduct(this, this.index);
        checkSpinnerValue();
    }
/**
    public void onClickBtnBasket(View v) {
        this.cancelImageButton.setEnabled(true);
        this.basket.addArticle(this.listProduitToShow.get(this.index).getId(), sizeSpinner.getSelectedItem().toString(), colorSpinner.getSelectedItem().toString());
        this.basketAmount += this.listProduitToShow.get(this.index).getPrice();
        showToastAddProductToBasket();
    }

    public void onClickCancel(View v) {
        if (this.basket.getBasketSize() > 0 && this.basket != null) {
            CancelAlert alert = new CancelAlert();
            alert.show(getSupportFragmentManager(), "Suppression");
        }
    }**/
    public void onClickImage(View v) {
        zoomImage();
    }

    public void onClickImageZoomed(View v) {
        unzoomImage();
    }
/**
    // ---- DIALOG ONCLICK ----
    public void onClick(DialogInterface dialog, int which) {
        if (which == -1) {
            this.basket.removeAllArticles();
            this.basketAmount = 0;
            Toast.makeText(this.getContext(), getString(R.string.clear_basket), Toast.LENGTH_LONG).show();
            this.cancelImageButton.setEnabled(false);
        } else {
            Toast.makeText(this.getContext(), getString(R.string.cancel_clear_basket), Toast.LENGTH_LONG).show();
        }
    }**/


    // ---- SPINNER EVENTS ----
    public void checkSpinnerValue() {
        if (!this.colorSpinner.getSelectedItem().toString().equals("Choix du coloris") && !this.sizeSpinner.getSelectedItem().toString().equals("Choix de la taille")) {
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
    public void getImage(Object[] results) {
        if (results[0] != null) {
            int idx = Integer.parseInt(results[1].toString());
            Bitmap img = (Bitmap) results[0];

            boolean imgNotFound = this.listImgProduitToShow.size() < index;

            if (imgNotFound) {
                int id = getResources().getIdentifier("img", "drawable", getActivity().getPackageName());
                this.pullImageView.setImageResource(id);
            } else {
                this.listImgProduitToShow.set(idx, img);
                this.pullImageView.setImageBitmap(this.listImgProduitToShow.get(index));
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Erreur JSON", error + "là");
        Toast.makeText(this.getContext(), R.string.error_bdd, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONObject o = response;
            Produit product = new Produit(o.getInt("id_produit"), o.getInt("id_categorie"), o.getDouble("prix"), o.getString("visuel"), o.getString("description"), o.getString("titre"));
            this.listProduitToShow.add(product);
            this.showPullInfo(this.index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}