package com.example.td1.ui.venteCatalogue;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.VolleyError;
import com.example.td1.ActivityWaitingImage;
import com.example.td1.DAO.ProductDAO;
import com.example.td1.ImageFromURL;
import com.example.td1.ActiviteECommerce;
import com.example.td1.R;
import com.example.td1.modele.Panier;
import com.example.td1.modele.Produit;
import com.example.td1.modele.Taille;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class VenteCatalogueFragment extends Fragment implements AdapterView.OnItemSelectedListener, ActivityWaitingImage, com.android.volley.Response.Listener<JSONArray>, com.android.volley.Response.ErrorListener {

    protected Button prevBtn;
    protected Button nextBtn;

    protected TextView priceTextView;
    protected TextView descriptionTextView;
    protected TextView titleTextView;
    protected TextView noProductsTextView;

    protected ImageView pullImageView;
    protected ImageView pullImageViewZoomed;

    protected boolean isImageZoomed;
    protected boolean alreadyHaveInfo;

    protected View whiteBackgroundView;
    protected View whiteBlankView;

    protected ImageButton basketImageButton;
    protected ImageButton filledHeartImageButton;
    protected ImageButton outlinedHeartImageButton;

    protected Spinner sizeSpinner;
    protected ArrayAdapter<String> sizeSpinnerArrayAdapter;

    protected static final int MAIN_VENTE = 0;

    protected ArrayList<Produit> listProduitToShow;
    protected ArrayList<Bitmap> listImgProduitToShow;
    protected ArrayList<String> listSizesLabels;

    protected Panier basket;

    protected int index;
    protected int productTableLength;
    protected int idCateg;
    protected int mode;
    
    protected String clothingSize = "";
    
    protected View root;
    public static final int RETOUR = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.vente_catalogue_fragment, container, false);

        this.listProduitToShow = new ArrayList<Produit>();
        this.listImgProduitToShow = new ArrayList<Bitmap>();
        this.listSizesLabels = new ArrayList<String>();
        this.alreadyHaveInfo = false;
        this.mode = this.getArguments().getInt("requestCode", 0);

        if (this.getActivity().getIntent().getSerializableExtra("newProduct") != null) {
            Produit productToAdd = (Produit) this.getActivity().getIntent().getSerializableExtra("newProduct");
            this.listProduitToShow.add(productToAdd);
        }

        if (savedInstanceState != null) {
            this.listProduitToShow = (ArrayList<Produit>) savedInstanceState.getSerializable("listProduitToShow");
            this.listImgProduitToShow = (ArrayList<Bitmap>) savedInstanceState.getSerializable("listImgProduitToShow");

            this.basket = ((ActiviteECommerce) this.getActivity()).getBasket();

            this.index = savedInstanceState.getInt("index");
            this.isImageZoomed = savedInstanceState.getBoolean("isImageZoomed");
            this.productTableLength = savedInstanceState.getInt("productTableLength");
            this.idCateg = savedInstanceState.getInt("idCateg");
            this.clothingSize = savedInstanceState.getString("taille");
            this.alreadyHaveInfo = true;
        } else {
            this.listProduitToShow = new ArrayList<Produit>();
            this.isImageZoomed = false;
            this.index = 0;

            this.basket = ((ActiviteECommerce) this.getActivity()).getBasket();
            this.idCateg = this.getArguments().getInt("id_categ", -1);

            if (this.idCateg != -1) {
                ProductDAO.findAllByCateg(this, this.idCateg, 1);
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
        this.prevBtn = this.root.findViewById(R.id.prevButton);
        this.nextBtn = this.root.findViewById(R.id.nextButton);

        // -- IMAGEBUTTONS --
        this.basketImageButton = this.root.findViewById(R.id.cartImageButton);
        this.filledHeartImageButton = this.root.findViewById(R.id.filledHeartImageButton);
        this.outlinedHeartImageButton = this.root.findViewById(R.id.outlinedHeartImageButton);

        // -- TEXTVIEWS --
        this.priceTextView = this.root.findViewById(R.id.priceTextView);
        this.descriptionTextView = this.root.findViewById(R.id.descriptionTextView);
        this.titleTextView = this.root.findViewById(R.id.titleTextView);
        this.noProductsTextView = this.root.findViewById(R.id.noProductsTextView);

        // -- IMAGEVIEWS --
        this.pullImageView = this.root.findViewById(R.id.productImageView);
        this.pullImageViewZoomed = this.root.findViewById(R.id.expandedImageView);

        this.pullImageView.setOnClickListener(this::onClickImage);
        this.pullImageViewZoomed.setOnClickListener(this::onClickImageZoomed);

        // -- LISTENERS
        this.basketImageButton.setOnClickListener(this::onClickBtnBasket);
        this.filledHeartImageButton.setOnClickListener(this::onClickBtnHeartFilled);
        this.outlinedHeartImageButton.setOnClickListener(this::onClickBtnHeartOutlined);
        this.nextBtn.setOnClickListener(this::onClickBtnNext);
        this.prevBtn.setOnClickListener(this::onClickBtnPrev);

        // -- SPINNERS --
        this.sizeSpinner = this.root.findViewById(R.id.sizeSpinner);
        this.sizeSpinner.setOnItemSelectedListener(this);

        // -- VIEWS --
        this.whiteBackgroundView = this.root.findViewById(R.id.blankView);
        this.whiteBlankView = this.root.findViewById(R.id.whiteBlankView);

        if (this.isImageZoomed) {
            zoomImage();
        }

        if (this.mode == MAIN_VENTE) {
            this.basketImageButton.setVisibility(View.VISIBLE);
        } else {
            this.basketImageButton.setVisibility(View.INVISIBLE);
        }

        if (this.alreadyHaveInfo) {
            this.showPullInfo(this.index);
            this.changeImageView(this.index);
            this.enablePrevNextButtons(this.index);
        }

        this.whiteBlankView.setVisibility(View.INVISIBLE);
        this.noProductsTextView.setVisibility(View.INVISIBLE);

    }

    // ------ ROTATION ---------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("index", this.index);
        outState.putSerializable("listProduitToShow", this.listProduitToShow);
        outState.putSerializable("listImgProduitToShow", this.listImgProduitToShow);
        outState.putSerializable("basket", this.basket);
        outState.putInt("productTableLength", this.productTableLength);
        outState.putInt("idCateg", this.idCateg);
        outState.putString("taille",this.sizeSpinner.getSelectedItem().toString());

        if (this.pullImageViewZoomed.getVisibility() == View.VISIBLE) {
            outState.putBoolean("isImageZoomed", true);
        } else {
            outState.putBoolean("isImageZoomed", false);
        }
    }

    // ----- VIEW ---------------------
    public void showPullInfo(int index) {
        this.priceTextView.setText(String.format(getString(R.string.price_text_view), this.listProduitToShow.get(index).getPrice()));
        this.descriptionTextView.setText(this.listProduitToShow.get(index).getDescription());
        this.titleTextView.setText(this.listProduitToShow.get(index).getTitle());

        this.listSizesLabels.clear();
        this.listSizesLabels.addAll(this.listProduitToShow.get(this.index).getSizesLabels());
        this.sizeSpinnerArrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, this.listSizesLabels);
        this.sizeSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        this.sizeSpinner.setAdapter(this.sizeSpinnerArrayAdapter);

        if (this.listProduitToShow.get(index).getFavori()) {
            this.filledHeartImageButton.setVisibility(View.VISIBLE);
            this.outlinedHeartImageButton.setVisibility(View.INVISIBLE);
        } else {
            this.filledHeartImageButton.setVisibility(View.INVISIBLE);
            this.outlinedHeartImageButton.setVisibility(View.VISIBLE);
        }
    }

    public void showToastAddProductToBasket() {
        switch (this.idCateg) {
            case 1:
                Toast.makeText(this.getContext(), String.format(getString(R.string.add_pull_basket), this.index), Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this.getContext(), String.format(getString(R.string.add_bonnet_basket), this.index), Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this.getContext(), String.format(getString(R.string.add_casquette_basket), this.index), Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this.getContext(), String.format(getString(R.string.add_article_basket), this.index), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void changeImageView(int index) {
        this.pullImageView.setImageBitmap(this.listImgProduitToShow.get(index));
        this.pullImageViewZoomed.setImageBitmap(this.listImgProduitToShow.get(index));
    }

    // ---- BUTTONS -------------------------
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

    // --- ZOOM -------------
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


    // --- FIND INDEX IN PRODUCT LIST
    public int getIndexById(int id) {
        for (int i = 0; i < this.listProduitToShow.size(); i++) {
            if (this.listProduitToShow.get(i) != null && this.listProduitToShow.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    // --- FIND ID OF SELECTED ITEM IN SPINNER
    public int getSpinnerSelectedSizeId(String sizeLabel) {
        ArrayList<Taille> sizes = this.listProduitToShow.get(this.index).getSizes();
        for (int i = 0; i < sizes.size(); i++) {
            if (sizes.get(i).getLabel() == sizeLabel) {
                return sizes.get(i).getId();
            }
        }
        return -1;
    }

    // --- CLICK EVENTS ----
    public void onClickBtnNext(View v) {
        this.index++;
        this.showPullInfo(this.index);
        this.changeImageView(this.index);
        this.enablePrevNextButtons(this.index);
        checkSpinnerValue();
    }

    public void onClickBtnPrev(View v) {
        this.index--;
        this.showPullInfo(this.index);
        this.changeImageView(this.index);
        this.enablePrevNextButtons(this.index);
        checkSpinnerValue();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClickBtnBasket(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_quantity, null);
        final EditText text = view.findViewById(R.id.editTextItemQuantity);

        builder.setTitle(getString(R.string.quantity));
        builder.setView(view);

        // Specifying a listener allows you to take an action before dismissing the dialog.
        // The dialog is automatically dismissed when a dialog button is clicked.
        builder.setPositiveButton(android.R.string.yes, (dialog, which) -> {
            final int quantity = Integer.parseInt(text.getText().toString());
            int sizeId = this.getSpinnerSelectedSizeId(sizeSpinner.getSelectedItem().toString());
            this.basket.addArticle(this.listProduitToShow.get(this.index), new Taille(this.getSpinnerSelectedSizeId(sizeSpinner.getSelectedItem().toString()), sizeSpinner.getSelectedItem().toString()), quantity);
            ((ActiviteECommerce) this.getActivity()).updateBasket(this.basket);

            showToastAddProductToBasket();
        });
        // A null listener allows the button to dismiss the dialog and take no further action.
        builder.setNegativeButton(android.R.string.no, null);
        builder.show();
    }


    public void onClickBtnHeartFilled(View v) {
        this.filledHeartImageButton.setVisibility(View.INVISIBLE);
        this.outlinedHeartImageButton.setVisibility(View.VISIBLE);

        ProductDAO.unstarProduct(this, this.listProduitToShow.get(this.index).getId(), 1);
        this.listProduitToShow.get(this.index).setFavori(false);
    }

    public void onClickBtnHeartOutlined(View v) {
        this.filledHeartImageButton.setVisibility(View.VISIBLE);
        this.outlinedHeartImageButton.setVisibility(View.INVISIBLE);

        ProductDAO.starProduct(this, this.listProduitToShow.get(this.index).getId(), 1);
        this.listProduitToShow.get(this.index).setFavori(true);
    }

    public void onClickImage(View v) {
        zoomImage();
    }

    public void onClickImageZoomed(View v) {
        unzoomImage();
    }

    // ---- DIALOG ONCLICK ----

    public void onClick(DialogInterface dialog, int which) {
        if (which == -1) {
            this.basket.removeAllArticles();
            Toast.makeText(this.getContext(), getString(R.string.clear_basket), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getContext(), getString(R.string.cancel_clear_basket), Toast.LENGTH_LONG).show();
        }
    }

    // ---- SPINNER EVENTS ----
    public void checkSpinnerValue() {
        if(!this.clothingSize.equals("")){
            this.sizeSpinner.setSelection(sizeSpinnerArrayAdapter.getPosition(this.clothingSize));
            this.clothingSize="";
        }
        if (!this.sizeSpinner.getSelectedItem().toString().equals("Choix de la taille")) {
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

    // ---- DATABASE EVENTS ------------------------
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
                if (idx == 0) {
                    this.changeImageView(this.index);
                }
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("test", error + "là");
        Toast.makeText(this.getContext(), R.string.error_bdd, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject o = response.getJSONObject(i);
                // test if it is an array of products or an array of sizes
                if (o.has("libelle")) { // Array of size
                    // Adding sizes for each product :)
                    int indexOfProductToChange = this.getIndexById(o.getInt("id_produit"));
                    this.listProduitToShow.get(indexOfProductToChange).addSize(new Taille(o.getInt("id_taille"), o.getString("libelle")));

                    Log.e("notify", "on a notifié que le dataset avait changé");

                    if (i == response.length() - 1) {
                        this.listSizesLabels.clear();
                        this.listSizesLabels.addAll(this.listProduitToShow.get(this.index).getSizesLabels());
                        this.sizeSpinnerArrayAdapter.notifyDataSetChanged();
                    }

                } else {
                    Produit product = new Produit(o.getInt("id_produit"), o.getInt("id_categorie"), o.getDouble("tarif"), o.getString("visuel"), o.getString("description"), o.getString("titre"), o.getBoolean("favori"), new ArrayList<>());
                    this.listProduitToShow.add(product);
                    this.showPullInfo(this.index);
                    this.enablePrevNextButtons(this.index);

                    if (i == response.length() - 1) {
                        ProductDAO.findAllSizesByCateg(this, this.idCateg);

                        for (int y = 0; y < this.listProduitToShow.size(); y++) {
                            this.listImgProduitToShow.add(null);
                            ImageFromURL loader = new ImageFromURL(this);
                            Log.e("getImage", String.valueOf("https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/" + this.listProduitToShow.get(y).getImgSrc()));
                            loader.execute("https://devweb.iutmetz.univ-lorraine.fr/~dumouli15u/DevMob/" + this.listProduitToShow.get(y).getImgSrc(), String.valueOf(y));
                        }
                    }
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}