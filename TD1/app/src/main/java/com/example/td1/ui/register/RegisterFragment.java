package com.example.td1.ui.register;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.td1.ActivityWaitingImage;
import com.example.td1.CategoriesAdapter;
import com.example.td1.DAO.CustomerDAO;
import com.example.td1.ImageFromURL;
import com.example.td1.ActiviteECommerce;
import com.example.td1.R;
import com.example.td1.modele.Categorie;
import com.example.td1.modele.Client;
import com.example.td1.modele.Panier;
import com.example.td1.modele.Produit;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Fragment implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener{

    private View root;
    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private EditText identifierEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText addrStreetEditText;
    private EditText addrPostalCodeEditText;
    private EditText addrCityEditText;
    private EditText addrCountryEditText;
    private EditText addrNumberEditText;
    private Button registerButton;
    private ArrayList<Pair<String, String>> errors;
    private ArrayList<Pair<EditText, String>> fields;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_register, container, false);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.fields = new ArrayList<Pair<EditText, String>>();
        this.errors = new ArrayList<Pair<String, String>>();

        this.firstnameEditText = this.root.findViewById(R.id.firstnameEditText);
        this.fields.add(new Pair(this.firstnameEditText, getString(R.string.firstname)));

        this.lastnameEditText = this.root.findViewById(R.id.lastnameEditText);
        this.fields.add(new Pair(this.lastnameEditText, getString(R.string.lastname)));

        this.identifierEditText = this.root.findViewById(R.id.idEditText);
        this.fields.add(new Pair(this.identifierEditText, getString(R.string.id)));

        this.passwordEditText = this.root.findViewById(R.id.passwordEditText);
        this.fields.add(new Pair(this.passwordEditText, getString(R.string.password)));

        this.confirmPasswordEditText = this.root.findViewById(R.id.confirmPasswordEditText);
        this.fields.add(new Pair(this.confirmPasswordEditText, getString(R.string.confirm)));

        this.addrCityEditText = this.root.findViewById(R.id.cityEditText);
        this.fields.add(new Pair(this.addrCityEditText, getString(R.string.city)));

        this.addrStreetEditText = this.root.findViewById(R.id.streetEditText);
        this.fields.add(new Pair(this.addrStreetEditText, getString(R.string.street)));

        this.addrPostalCodeEditText = this.root.findViewById(R.id.postalCodeEditText);
        this.fields.add(new Pair(this.addrPostalCodeEditText, getString(R.string.postal_code)));

        this.addrCountryEditText = this.root.findViewById(R.id.countryEditText);
        this.fields.add(new Pair(this.addrCountryEditText, getString(R.string.country)));

        this.addrNumberEditText = this.root.findViewById(R.id.numberEditText);
        this.fields.add(new Pair(this.addrNumberEditText, getString(R.string.number)));

        this.registerButton = this.root.findViewById(R.id.registerButton);
        this.registerButton.setOnClickListener(this::onClickRegister);
    }

    public void onClickRegister(View v) {
        this.validateFields();
    }

    public void validateFields() {
        for (Pair<EditText, String> input: this.fields) {

            if (TextUtils.isEmpty(input.first.getText().toString())) {
                this.errors.add(new Pair(input.second, String.format(getString(R.string.empty_field), input.second)));
            } else {
                this.errors.remove(new Pair(input.second, String.format(getString(R.string.empty_field), input.second)));

                int inputTypeValue = input.first.getInputType();
                if (inputTypeValue == InputType.TYPE_CLASS_NUMBER) {
                    // test if it is a number
                    if (!input.first.getText().toString().matches("\\d+(?:\\.\\d+)?")) {
                        this.errors.add(new Pair(input.second, String.format(getString(R.string.field_is_not_number), input.second)));
                    } else {
                        this.errors.remove(new Pair(input.second, String.format(getString(R.string.field_is_not_number), input.second)));
                    }
                    
                } else if (input.second == getString(R.string.confirm)) {
                    this.testPasswordMatch(input.first, this.passwordEditText);
                } else if (input.second == getString(R.string.id)) {
                    CustomerDAO.doesIdentifierAlreadyExist(this, input.first.getText().toString());
                }
            }
        }
    }

    public void testPasswordMatch(EditText inputConfirm, EditText inputPassword) {
        if (TextUtils.isEmpty(inputPassword.getText().toString()) || TextUtils.isEmpty(inputConfirm.getText().toString())) {
            this.errors.add(new Pair(getString(R.string.password), getString(R.string.must_field_password_fields)));
        } else {
            this.errors.remove(new Pair(getString(R.string.password), getString(R.string.must_field_password_fields)));

            if (!inputPassword.getText().toString().equals(inputConfirm.getText().toString())) {
                this.errors.add(new Pair(getString(R.string.confirm), getString(R.string.passwords_must_match)));
            } else {
                this.errors.remove(new Pair(getString(R.string.confirm), getString(R.string.passwords_must_match)));
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Volley error", String.valueOf(error));
        Toast.makeText(this.getContext(), R.string.error_db, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {

            if (response.has("identifierAlreadyExists")) {

                if (response.getBoolean("identifierAlreadyExists")) {

                    this.errors.add(new Pair(getString(R.string.id), getString(R.string.identifier_already_exists)));
                    Toast.makeText(this.getContext(), getString(R.string.identifier_already_exists), Toast.LENGTH_SHORT).show();

                } else {

                    this.errors.remove(new Pair(getString(R.string.id), getString(R.string.identifier_already_exists)));

                    if (this.errors.isEmpty()) {
                        // no errors = we can register the new customer :)
                        Client customer = new Client(
                                this.firstnameEditText.getText().toString(),
                                this.lastnameEditText.getText().toString(),
                                this.identifierEditText.getText().toString(),
                                this.passwordEditText.getText().toString(),
                                this.addrStreetEditText.getText().toString(),
                                Integer.parseInt(this.addrPostalCodeEditText.getText().toString()),
                                Integer.parseInt(this.addrNumberEditText.getText().toString()),
                                this.addrCityEditText.getText().toString(),
                                this.addrCountryEditText.getText().toString()
                        );

                        try {
                            JSONObject customerJson = new JSONObject(customer.toJson());
                            CustomerDAO.registerCustomer(this, customerJson);
                        } catch (Exception e) {
                            Log.e("Error", String.valueOf(e));
                        }

                    } else {
                        Toast.makeText(this.getContext(), this.errors.get(0).second, Toast.LENGTH_SHORT).show();
                    }

                }

            } else if (response.getInt("id") != -1) {
                Toast.makeText(this.getContext(), getString(R.string.account_created), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}