package com.example.td1.ui.editPersonalInfo;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.td1.ActivityLogin;
import com.example.td1.R;
import com.example.td1.modele.Client;
import com.example.td1.ui.register.RegisterFragment;

public class EditPersonalInfoFragment extends RegisterFragment {

    private Button saveButton;
    private EditText oldPasswordEditText;

    @Override
    public void onStart() {
        super.onStart();

        Client customer = ((ActivityLogin) this.getActivity()).getLoggedInCustomer();

        // Init save btn and current password
        this.saveButton = this.root.findViewById(R.id.saveButton);
        this.oldPasswordEditText = this.root.findViewById(R.id.oldPasswordEditText);

        // Change visibility for btns and password
        this.saveButton.setVisibility(View.VISIBLE);
        this.oldPasswordEditText.setVisibility(View.VISIBLE);

        this.registerButton.setVisibility(View.INVISIBLE);
        this.passwordEditText.setVisibility(View.INVISIBLE);

        // Fill the fields
        this.firstnameEditText.setText(customer.getFirstname());
        this.lastnameEditText.setText(customer.getLastname());
        this.identifierEditText.setText(customer.getIdentifier());
        this.oldPasswordEditText.setText(customer.getPassword());
        this.confirmPasswordEditText.setText(customer.getPassword());
        this.addrNumberEditText.setText(String.valueOf(customer.getAddrNumber()));
        this.addrStreetEditText.setText(customer.getAddrStreet());
        this.addrPostalCodeEditText.setText(String.valueOf(customer.getAddrPostalCode()));
        this.addrCityEditText.setText(customer.getAddrCity());
        this.addrCountryEditText.setText(customer.getAddrCountry());
    }
}
