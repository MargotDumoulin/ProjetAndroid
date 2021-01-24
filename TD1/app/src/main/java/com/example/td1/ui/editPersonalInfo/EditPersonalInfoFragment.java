package com.example.td1.ui.editPersonalInfo;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.td1.ActivityLogin;
import com.example.td1.DAO.CustomerDAO;
import com.example.td1.R;
import com.example.td1.modele.Client;
import com.example.td1.ui.register.RegisterFragment;
import com.example.td1.utils.Triplet;

import org.json.JSONException;
import org.json.JSONObject;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class EditPersonalInfoFragment extends RegisterFragment {

    private Button saveButton;
    private String customerPassword;
    private Client customer;
    private boolean hasInfo;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_register, container, false);

        this.customer = ((ActivityLogin) this.getActivity()).getLoggedInCustomer();
        this.customerPassword = this.customer.getPassword();
        this.hasInfo = false;

        if (savedInstanceState != null) {
            this.hasInfo = savedInstanceState.getBoolean("hasInfo");
        }

        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("hasInfo", true);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Init save btn and current password
        this.saveButton = this.root.findViewById(R.id.saveButton);

        this.fields.add(new Pair(this.currentPasswordEditText, getString(R.string.currentPassword)));
        this.fields.add(new Pair(this.newPasswordEditText, getString(R.string.newPassword)));

        // Change visibility for btns and password
        this.saveButton.setVisibility(View.VISIBLE);
        this.currentPasswordEditText.setVisibility(View.VISIBLE);
        this.newPasswordEditText.setVisibility(View.VISIBLE);

        this.registerButton.setVisibility(View.INVISIBLE);
        this.passwordEditText.setVisibility(View.INVISIBLE);

        if (!this.hasInfo) {
            // Fill the fields
            this.firstnameEditText.setText(this.customer.getFirstname());
            this.lastnameEditText.setText(this.customer.getLastname());
            this.identifierEditText.setText(this.customer.getIdentifier());
            this.addrNumberEditText.setText(String.valueOf(this.customer.getAddrNumber()));
            this.addrStreetEditText.setText(this.customer.getAddrStreet());
            this.addrPostalCodeEditText.setText(String.valueOf(this.customer.getAddrPostalCode()));
            this.addrCityEditText.setText(this.customer.getAddrCity());
            this.addrCountryEditText.setText(this.customer.getAddrCountry());
        }

        this.saveButton.setOnClickListener(this::onClickSave);
    }

    @Override
    public void validateIdentifierField() {
        if (this.identifierEditText.getText().toString().trim().toLowerCase().equals(this.customer.getIdentifier().toLowerCase())) {
            this.updateCustomerInfo();
        } else {
            CustomerDAO.doesIdentifierAlreadyExist(this, this.identifierEditText.getText().toString());
        }
    }

    public void filterFieldsAndValidate() {
        for (int i = 0; i < this.fields.size(); i++) {
            Pair<EditText, String> field = this.fields.get(i);

            if (field.second.equals(getString(R.string.confirm))
                    || field.second.equals(getString(R.string.currentPassword))
                    || field.second.equals(getString(R.string.newPassword))
                    || field.second.equals(getString(R.string.password))
            ) {
                this.fields.remove(i);
                i--;
            }
        }

        this.validateFields();
    }

    public void updateCustomerInfo() {
        if (this.errors.isEmpty()) {
            String password = null;

            if (!this.newPasswordEditText.getText().toString().isEmpty()) {
                password = this.newPasswordEditText.getText().toString();
            } else {
                password = this.currentPasswordEditText.getText().toString();
            }

            Client customer = new Client(
                    this.customer.getId(),
                    this.firstnameEditText.getText().toString().trim(),
                    this.lastnameEditText.getText().toString().trim(),
                    this.identifierEditText.getText().toString().trim(),
                    password,
                    this.addrStreetEditText.getText().toString().trim(),
                    Integer.parseInt(this.addrPostalCodeEditText.getText().toString()),
                    Integer.parseInt(this.addrNumberEditText.getText().toString()),
                    this.addrCityEditText.getText().toString().trim(),
                    this.addrCountryEditText.getText().toString().trim()
            );

            try {
                JSONObject customerJson = new JSONObject(customer.toJson());
                CustomerDAO.updateCustomer(this, customerJson);
            } catch (Exception e) {
                Log.e("Error", String.valueOf(e));
            }
        } else {
            Toast.makeText(this.getContext(), this.errors.get(0).third, Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickSave(View view) {
        if (this.currentPasswordEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this.getContext(), getString(R.string.empty_current_password), Toast.LENGTH_LONG).show();
        } else if (!(BCrypt.verifyer().verify(this.currentPasswordEditText.getText().toString().toCharArray(), this.customerPassword)).verified) {
            Toast.makeText(this.getContext(), getString(R.string.wrong_current_password), Toast.LENGTH_LONG).show();
        } else if (this.newPasswordEditText.getText().toString().trim().length() > 0) {
            if (!this.newPasswordEditText.getText().toString().trim().equals(this.confirmPasswordEditText.getText().toString().trim())) {
                Toast.makeText(this.getContext(), getString(R.string.passwords_does_not_match), Toast.LENGTH_LONG).show();
            } else {
                this.filterFieldsAndValidate();
            }
        } else {
            this.filterFieldsAndValidate();
        }
    }

    public void onResponse(JSONObject response) {
        try {
            if (response.has("identifierAlreadyExists")) {

                if (response.getBoolean("identifierAlreadyExists")) {
                    this.errors.add(new Triplet(getString(R.string.id), getString(R.string.identifier_already_exists), getString(R.string.identifier_already_exists)));
                    Toast.makeText(this.getContext(), getString(R.string.identifier_already_exists), Toast.LENGTH_SHORT).show();
                } else {
                    this.updateCustomerInfo();
                }
            } else if (response.getInt("id") != -1) {
                String hashedPassword = response.getString("password");

                Client updatedCustomer = new Client(
                        this.customer.getId(),
                        this.firstnameEditText.getText().toString().trim(),
                        this.lastnameEditText.getText().toString().trim(),
                        this.identifierEditText.getText().toString().trim(),
                        hashedPassword,
                        this.addrStreetEditText.getText().toString().trim(),
                        Integer.parseInt(this.addrPostalCodeEditText.getText().toString()),
                        Integer.parseInt(this.addrNumberEditText.getText().toString()),
                        this.addrCityEditText.getText().toString().trim(),
                        this.addrCountryEditText.getText().toString().trim()
                );

                ((ActivityLogin) this.getActivity()).updateLoggedInCustomer(updatedCustomer);
                ((ActivityLogin) this.getActivity()).updateDrawerWithCustomerInfo(updatedCustomer);

                Toast.makeText(this.getContext(), getString(R.string.customer_successfully_updated), Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}