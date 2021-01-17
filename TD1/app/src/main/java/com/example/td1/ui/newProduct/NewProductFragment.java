package com.example.td1.ui.newProduct;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.td1.R;
import com.example.td1.modele.Produit;

import java.util.ArrayList;

public class NewProductFragment extends Fragment {

    private EditText titleEditText;
    private EditText priceEditText;
    private EditText descriptionEditText;

    private static final int MAIN_CATALOGUE = 1;

    private View root;
    private Button createButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_new_product, container, false);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.priceEditText = this.root.findViewById(R.id.productPriceEditText);
        this.titleEditText = this.root.findViewById(R.id.productTitleEditText);
        this.descriptionEditText = this.root.findViewById(R.id.productDescriptionEditText);
        this.createButton = this.root.findViewById(R.id.createButton);
        this.createButton.setOnClickListener(this::onClickAddProduct);
    }

    public void onClickAddProduct(View v) {
        if (!this.titleEditText.getText().toString().equals("") && !this.descriptionEditText.getText().toString().equals("") && !this.priceEditText.getText().toString().equals("")) {
            Bundle bundle = new Bundle();
            bundle.putInt("id_categ", 0);
            bundle.putSerializable("newProduct", new Produit(5, 0, Double.parseDouble(String.valueOf(this.priceEditText.getText())), "img", String.valueOf(this.descriptionEditText.getText()), String.valueOf(this.titleEditText.getText()), new ArrayList<>()));
            Navigation.findNavController(v).navigate(R.id.action_newProductFragment_to_venteCatalogueFragment, bundle);

        } else {
            Toast.makeText(this.getContext(), getString(R.string.fill_all_fields), Toast.LENGTH_LONG).show();
        }
    }

}